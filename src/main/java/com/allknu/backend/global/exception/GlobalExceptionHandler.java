package com.allknu.backend.global.exception;


import com.allknu.backend.global.exception.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleParamViolationException(BindException ex) {
        // 파라미터 validation에 걸렸을 경우

        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getStatus().value())
                .message(ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getMessage())
                .code(ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getCode())
                .build();
        return new ResponseEntity<>(response, ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        // 없는 경로로 요청 시

        ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.NOT_FOUND_PATH.getMessage())
                .status(ErrorCode.NOT_FOUND_PATH.getStatus().value())
                .code(ErrorCode.NOT_FOUND_PATH.getCode())
                .build();

        return new ResponseEntity<>(response, ErrorCode.NOT_FOUND_PATH.getStatus());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        // GET POST 방식이 잘못된 경우

        ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.METHOD_NOT_ALLOWED.getMessage())
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .code(ErrorCode.METHOD_NOT_ALLOWED.getCode())
                .build();

        return new ResponseEntity<>(response, ErrorCode.METHOD_NOT_ALLOWED.getStatus());
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getMessage())
                .status(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getStatus().value())
                .code(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode())
                .build();

        return new ResponseEntity<>(response, ErrorCode.UNSUPPORTED_MEDIA_TYPE.getStatus());
    }
    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(CustomJwtRuntimeException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.INVALID_JWT_TOKEN.getCode())
                .message(ErrorCode.INVALID_JWT_TOKEN.getMessage())
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.INVALID_JWT_TOKEN.getStatus());
    }
    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.LOGIN_FAILED.getCode())
                .message(ErrorCode.LOGIN_FAILED.getMessage())
                .status(ErrorCode.LOGIN_FAILED.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.LOGIN_FAILED.getStatus());
    }
    @ExceptionHandler(KnuApiCallFailedException.class)
    protected ResponseEntity<ErrorResponse> handleKnuApiCallFailedException(KnuApiCallFailedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.KNU_API_FAILED.getCode())
                .message(ErrorCode.KNU_API_FAILED.getMessage())
                .status(ErrorCode.KNU_API_FAILED.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.KNU_API_FAILED.getStatus());
    }
    @ExceptionHandler(RestaurantNameDuplicatedException.class)
    protected ResponseEntity<ErrorResponse> handleRestaurantNameDuplicateddException(RestaurantNameDuplicatedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.RESTAURANT_NAME_DUPLICATED.getCode())
                .message(ErrorCode.RESTAURANT_NAME_DUPLICATED.getMessage())
                .status(ErrorCode.RESTAURANT_NAME_DUPLICATED.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.RESTAURANT_NAME_DUPLICATED.getStatus());
    }
    @ExceptionHandler(NotFoundRestaurantException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundRestaurantException(NotFoundRestaurantException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_RESTAURANT.getCode())
                .message(ErrorCode.NOT_FOUND_RESTAURANT.getMessage())
                .status(ErrorCode.NOT_FOUND_RESTAURANT.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.NOT_FOUND_RESTAURANT.getStatus());
    }
    @ExceptionHandler(NotFoundMenuException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundMenuException(NotFoundMenuException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_MENU.getCode())
                .message(ErrorCode.NOT_FOUND_MENU.getMessage())
                .status(ErrorCode.NOT_FOUND_MENU.getStatus().value())
                .build();

        return new ResponseEntity<>(response, ErrorCode.NOT_FOUND_MENU.getStatus());
    }
    @ExceptionHandler(StationNameDuplicatedException.class)
    protected ResponseEntity<ErrorResponse> handleStationNameDuplicatedException(StationNameDuplicatedException e) {
        ErrorCode errorCode = ErrorCode.STATION_NAME_DUPLICATED;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
    @ExceptionHandler(NotFoundStationException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundStationException(NotFoundStationException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_STATION;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
    @ExceptionHandler(StationTimeDuplicatedException.class)
    protected ResponseEntity<ErrorResponse> handleStationTimeDuplicatedException(StationTimeDuplicatedException e) {
        ErrorCode errorCode = ErrorCode.STATION_TIME_DUPLICATED;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
    @ExceptionHandler(NotFoundStationTimetableException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundStationTimetableException(NotFoundStationTimetableException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_STATION_TIMETABLE;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
    @ExceptionHandler(KnuReadTimeOutException.class)
    protected ResponseEntity<ErrorResponse> handleKnuReadTimeOutException(KnuReadTimeOutException e) {
        ErrorCode errorCode = ErrorCode.KNU_READ_TIMEOUT;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
    @ExceptionHandler(NotFoundMapMarkerException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundMapMarkerException(NotFoundMapMarkerException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_MAP_MARKER;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(FcmClientFailedException.class)
    protected ResponseEntity<ErrorResponse> handleFcmClientFailedException(FcmClientFailedException e) {
        ErrorCode errorCode = ErrorCode.FCM_CLIENT_FAILED;

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
