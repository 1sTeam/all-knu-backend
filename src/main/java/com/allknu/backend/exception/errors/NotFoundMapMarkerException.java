package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class NotFoundMapMarkerException extends RuntimeException{
    public NotFoundMapMarkerException(){ super(ErrorCode.NOT_FOUND_MAP_MARKER.getMessage()); }

    public NotFoundMapMarkerException(Exception ex){
        super(ex);
    }
}
