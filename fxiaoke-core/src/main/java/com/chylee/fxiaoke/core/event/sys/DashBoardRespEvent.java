package com.chylee.fxiaoke.core.event.sys;

import com.chylee.fxiaoke.common.event.DataRespEvent;
import com.chylee.fxiaoke.common.model.JobLog;

public class DashBoardRespEvent extends DataRespEvent<JobLog> {
    private int jobTypeCount = 0;
    private int executorCount = 0;
    private int qrtzLogCount = 0;
    private int errorCount = 0;

    public int getJobTypeCount() {
        return jobTypeCount;
    }

    public void setJobTypeCount(int jobTypeCount) {
        this.jobTypeCount = jobTypeCount;
    }

    public int getExecutorCount() {
        return executorCount;
    }

    public void setExecutorCount(int executorCount) {
        this.executorCount = executorCount;
    }

    public int getQrtzLogCount() {
        return qrtzLogCount;
    }

    public void setQrtzLogCount(int qrtzLogCount) {
        this.qrtzLogCount = qrtzLogCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
