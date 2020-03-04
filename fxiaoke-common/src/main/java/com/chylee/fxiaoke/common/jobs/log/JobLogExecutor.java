package com.chylee.fxiaoke.common.jobs.log;

import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.exception.ErpApiException;
import com.chylee.fxiaoke.common.exception.ErpDataException;

public interface JobLogExecutor {
    boolean isSupport(int typeId, String executor, String apiName);
    void execute(int qrtzId, int typeId, String apiName)
            throws AccessTokenException, CrmDataException, ErpApiException, ErpDataException;
}
