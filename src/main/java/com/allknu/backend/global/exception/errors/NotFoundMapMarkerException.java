package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class NotFoundMapMarkerException extends RuntimeException{
    public NotFoundMapMarkerException(){ super(ErrorCode.NOT_FOUND_MAP_MARKER.getMessage()); }

    public NotFoundMapMarkerException(Exception ex){
        super(ex);
    }
}
