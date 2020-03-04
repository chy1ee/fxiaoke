package com.chylee.fxiaoke.common.jobs;

import java.util.concurrent.LinkedBlockingQueue;

public class JobDetailQueue <T> extends LinkedBlockingQueue<T> {
    private Object lock = new Object();

    private boolean finished;

    public JobDetailQueue () {
        super(100);
        this.finished = false;
    }

    public boolean isFinished() {
        synchronized (lock) {
            return finished;
        }
    }

    public void setFinished(boolean finished) {
        synchronized (lock) {
            this.finished = finished;
        }
    }
}
