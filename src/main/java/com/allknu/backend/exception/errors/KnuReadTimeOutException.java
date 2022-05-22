package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnuReadTimeOutException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(KnuReadTimeOutException.class);

    public KnuReadTimeOutException() {
        super(ErrorCode.KNU_READ_TIMEOUT.getMessage());
    }
    public KnuReadTimeOutException(String target) {
        super(ErrorCode.KNU_READ_TIMEOUT.getMessage());
        logger.error(target + "서버 read time out");
    }

    public KnuReadTimeOutException(Exception ex){
        super(ex);
    }
}