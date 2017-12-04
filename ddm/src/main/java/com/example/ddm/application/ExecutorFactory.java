package com.example.ddm.application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bbacr on 2017/7/11.
 *
 */

public class ExecutorFactory {
    private static ExecutorService mService;
    private ExecutorFactory(){
        mService = Executors.newCachedThreadPool();
    }
    private static class SingletonHolder{
        private static ExecutorFactory mInstance = new ExecutorFactory();
    }
    public static ExecutorFactory instance(){
        return SingletonHolder.mInstance;
    }
    public ExecutorService getExecutorService(){
        return mService;
    }

}
