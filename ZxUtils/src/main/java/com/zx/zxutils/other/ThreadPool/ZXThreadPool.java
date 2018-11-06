package com.zx.zxutils.other.ThreadPool;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xiangb on 2017/4/13.
 * 功能：
 */

public class ZXThreadPool {

    private static int cpu_num = Runtime.getRuntime().availableProcessors();

    private static ZXThreadPoolExecutor priorityThreadPool;

    public static void execute(ZXRunnable runnable) {
        if (priorityThreadPool == null) {
            priorityThreadPool = new ZXThreadPoolExecutor(cpu_num + 1, cpu_num * 2 + 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue());
        }
        priorityThreadPool.execute(runnable);
    }

    /**
     * 在终止前允许执行以前提交的任务。
     */
    public static void shutDown() {
        priorityThreadPool.shutdown();
    }

    /**
     * 重新创建一个线程池
     */
    public static void reCreatePool() {
        priorityThreadPool = new ZXThreadPoolExecutor(cpu_num + 1, cpu_num * 2 + 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue());
    }

    /**
     * 阻止正在任务队列中等待任务的启动并试图停止当前正在执行的任务。
     */
    public static void shutDownNow() {
        priorityThreadPool.shutdownNow();
    }

    /**
     * 移除线程
     *
     * @param runnable
     */
    public static void remove(ZXRunnable runnable) {
        try {
            priorityThreadPool.remove(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程池是否关闭
     *
     * @return
     */
    public static boolean isShutDown() {
        return priorityThreadPool.isShutdown();
    }

    /**
     * 线程暂停
     */
    public static void pause() {
        priorityThreadPool.pause();
    }

    /**
     * 线程恢复
     */
    public static void resume() {
        priorityThreadPool.resume();
    }

    /**
     * 是否为暂停状态
     *
     * @return
     */
    public static boolean isPaused() {
        return priorityThreadPool.isPaused();
    }


}
