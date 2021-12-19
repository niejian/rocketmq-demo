package com.code4fun.mq.utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @className: CustGlobalThreadPool
 * @desc: 自定义线程池
 * @time: 2021/12/19 8:49
 * @version: 0.0.1
 */
public class CustGlobalThreadPool {
    private static ExecutorService executorService;
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    static {
        initPool();
    }
    public static synchronized void initPool() {
        if (null != executorService) {
            executorService.shutdown();
        }

        executorService = new ThreadPoolExecutor(
                corePoolSize * 2,
                corePoolSize * 4,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                new BasicThreadFactory.Builder()
                        .namingPattern("GlobalThreadPool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static synchronized void shutDownPool(boolean now) {
        if (null != executorService) {
            if (now) {
                executorService.shutdownNow();
            } else {
                executorService.shutdown();
            }
        }
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void setExecutorService(ExecutorService executorService) {
        CustGlobalThreadPool.executorService = executorService;
    }

    public static <T> CompletableFuture<T> submit(Callable<T> task) {
        return (CompletableFuture<T>) executorService.submit(task);
    }

    public static <T> CompletableFuture<T> submit(Runnable task) {
        return (CompletableFuture<T>) executorService.submit(task);
    }
}
