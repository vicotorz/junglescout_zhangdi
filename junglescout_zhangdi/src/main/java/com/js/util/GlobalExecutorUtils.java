package com.js.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>全局的线程池</p>
 */
public class GlobalExecutorUtils {

    private static int cpuCnt = Runtime.getRuntime().availableProcessors();


    private static ExecutorService ioPool = new ThreadPoolExecutor(
            cpuCnt * 100, cpuCnt * 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(102400));


    public static ExecutorService getIOThreadPool() {
        return ioPool;
    }

}
