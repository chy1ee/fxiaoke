package com.chylee.fxiaoke.common.jobs;

import com.chylee.fxiaoke.common.model.JobDetail;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class JobDetailQueue extends LinkedBlockingQueue<JobDetail> {
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
