package com.linwl.weatherforecast.utils;

import java.text.MessageFormat;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 9:59
 * @description ：
 * @modified By：
 */
public class TaskThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public TaskThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String namePrefix =MessageFormat.format("worker<{0}>",threadNumber.getAndIncrement());
        Thread t = new Thread(group, r,
                namePrefix,
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
