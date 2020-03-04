package com.chylee.fxiaoke.common.jobs.detail;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractJobDetailExecutor extends ComplieModeSupported implements JobDetailExecutor {
    protected Logger logger = LoggerFactory.getLogger(AbstractJobDetailExecutor.class);

    protected void Debug(String msg, Object... objects) {
        if (logger.isDebugEnabled())
            logger.debug(msg, objects);
    }
}
