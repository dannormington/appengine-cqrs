package com.cqrs.appengine.core.messaging;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.appengine.api.ThreadManager;

/**
 * Class that supports executing a runnable command on another thread
 */
class ThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        if(command != null){
            ThreadFactory threadFactory = ThreadManager.backgroundThreadFactory();
            ExecutorService service = Executors.newCachedThreadPool(threadFactory);
            service.execute(command);
            }
        }
    }