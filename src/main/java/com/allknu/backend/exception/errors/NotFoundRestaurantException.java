package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class NotFoundRestaurantException extends RuntimeException {

    public NotFoundRestaurantException(){
        super(ErrorCode.NOT_FOUND_RESTAURANT.getMessage());
    }

    public NotFoundRestaurantException(Exception ex){
        super(ex);
    }
}
