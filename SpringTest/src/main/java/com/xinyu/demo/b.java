package com.xinyu.demo;


import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
class mythread implements Callable<Integer>
        {

            public Integer call() throws Exception {
                return null;
            }
        }

public class b {
    private  static int i=0;



    public static void main(String[] args) throws InterruptedException {

        Semaphore Semaphore = new Semaphore(10);
        Semaphore.acquire(3);
        Semaphore.release(2);
       /* FutureTask<Integer> futureTask = new FutureTask<Integer>(new mythread());
        ArrayList<String> list = new ArrayList<String>();
        List<String> list2 = new Vector<String>();
        List<String> list3 = Collections.synchronizedList(new ArrayList<String>());
        List<String> list4 = new CopyOnWriteArrayList<String>();
        list4.get(0);
        AtomicReference<Integer> atomicReference = new AtomicReference<Integer>();
        atomicReference.set(0);
        atomicReference.compareAndSet(0,6);
        atomicReference.compareAndSet(6,7);
        System.out.println(atomicReference.get().toString());*/

        WeakHashMap weakHashMap = new WeakHashMap();
        Integer key =1;
        weakHashMap.put(key,1);
        System.out.println(weakHashMap);
        key =null;
        System.gc();
        System.out.println("第二次"+weakHashMap);
        System.gc();
        System.out.println("第三次"+weakHashMap);
        int arr [] = new int[]{5,2,3,1};
        bubbleSort(arr);
       // quickSort(arr,0,arr.length-1);
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);

        }

       // Thread.sleep(Integer.MAX_VALUE);
    }
    //冒泡 本质是遍历数组 然后从第一个元素开始比较前一个元素 j+1 >j 那么把j+1位置的元素和j交换，以此类推，这样可以保证最左边是最大的 也就是倒序 反之升序
    public static void bubbleSort(int arr[]) {
        boolean flag =false;
        for(int i =arr.length-1 ; i>0; i--) {
            flag =false;

            for(int j=0 ; j<i ; j++) {
                //比较
                if(arr[j]>arr[j+1])
                {
                 //交换
                 int temp = arr[j+1];
                 arr[j+1] = arr[j];
                 arr[j] = temp;
                 flag =true;
                }
            }
            if(!flag)break;
        }

    }

    //快排
    public static void quickSort(int arr[],int left,int right){
        if(left>right)
            return;
        //基准数字
        int base = arr[left];
        //定义变量
        int i=left,j=right;
        //开始排序
        while (i<j){
            //先排右边的 如果比基准数小 则找下一个 碰到小于基准数字的就停下来，右边排完排左边  左边相反要比基准数大才停
            while (arr[j]>=base && i<j) j--;
            while (arr[i]<=base && i<j) i++;

            //交换这两个值 让大于基准数的去右边 小于基准数字的到左边来
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        //跳出循环后 说明i和j相遇了 那么此时就要吧这个值 和基准数交换 然后再反复上述逻辑 排左边，排完之后排右边
        arr[left] = arr[i];
        arr[i] = base;

        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);

    }


    public void quickshorts(int arr[],int left,int right){
        if (left>right)
            return;

        int base = arr[left];
        int i = left,j=right;
        while (i<j)
        {
            while (arr[j]>=base&& i<j)j--;
            while (arr[i]<=base&& i<j)i++;

            int temp = arr[i];
            arr[i] =arr[j] ;
            arr[j] = temp;
        }

        int temp = arr[i];
        arr[i] = base;
        arr[left]=temp;


        quickshorts(arr,left,i-1);
        quickshorts(arr,i+1,right);

    }


}
