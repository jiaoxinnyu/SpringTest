package com.xinyu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author jiaoxy
 * 线程池工具类
 */
public class ThreadPoolUtils {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolUtils.class);

    /** 线程池构造*/
    private ThreadPoolExecutor threadPool;
    private static volatile ThreadPoolUtils instance;

    /** 获取本地机器CPU线程个数*/
    private static int availableProcessors = Runtime.getRuntime().availableProcessors()>0 ? Runtime.getRuntime().availableProcessors():6;
    /**设置核心线程数量*/
    private static int corePoolSize = (availableProcessors>4) ? 4:availableProcessors;
    /**线程池阻塞队列*/
    private static BlockingQueue blockingQueue = new ArrayBlockingQueue<Runnable>(512);
    /**构造方法*/
    private ThreadPoolUtils() {
        logger.info("初始化ThreadPoolUtil参数,核心线程数-{},最大线程数-{}",corePoolSize,availableProcessors);
        threadPool = new ThreadPoolExecutor(corePoolSize, availableProcessors * 2, 30, TimeUnit.SECONDS, blockingQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    /**获取实例*/
    public static ThreadPoolUtils getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtils.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 提交任务不带返回值
     * @param command
     */
    public void execute(Runnable command) {
        threadPool.execute(command);
    }

    /**
     * 提交任务带返回值
     * @param callable
     * @return
     */
    public Future submit(Callable callable) { return threadPool.submit(callable); }


    /**
     * 打印一下各项指标
     */
    public void ThreadPoolMonitorToString(){
        int corePoolSize = threadPool.getCorePoolSize();
        int poolSize = threadPool.getPoolSize();
        int largestPoolSize = threadPool.getLargestPoolSize();
        int maximumPoolSize = threadPool.getMaximumPoolSize();
        int activeCount = threadPool.getActiveCount();
        int size = threadPool.getQueue().size();
        logger.info(String.format("corePoolSize=%s poolSize=%s,largestPoolSize=%s,maximumPoolSize=%s,activeCount=%s,queueSize=%s",corePoolSize,poolSize,largestPoolSize,maximumPoolSize,activeCount,size));
    }

    /**
     * 关闭
     */
    public void shutdown() {
        logger.info("shutdown ->");
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }
}
