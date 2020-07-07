package com.xinyu.demo;

import org.testng.annotations.AfterTest;

public class Test {
    public static void main(String[] args) {
        Base obj = new Sub();
        System.out.println(obj.x);
        System.out.println(obj.getX());





    }


    public static class Base {
        static int s =3;
        static {
            System.out.println("父静态代码块"+s);
        }
        int x = 1;
        public Base() {
            System.out.println("父类");
            this.echo();
            this.x = 2;
        }
        private void echo() {
            System.out.println("父类echo");
            System.out.println("Base.x="+this.x);
        }
        public int getX() {
            System.out.println("父类getx");
            return this.x;
        }
    }

    public  static class Sub extends Base {
        static int s =33;
        static {
            System.out.println("子静态代码块"+s);
        }
        int x = 3;
        public Sub() {
            System.out.println("子类");
            this.echo();
            this.x = 4;
        }
        private void echo() {
            System.out.println("子类echo");
            System.out.println("Sub.x="+this.x);
        }
        public int getX() {
            System.out.println("子类getx");
            return  this.x;
        }
    }

}
