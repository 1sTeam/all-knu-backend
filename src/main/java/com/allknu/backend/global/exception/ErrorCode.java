package com.allknu.backend.global.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "PATH_001", "NOT FOUND PATH"), // 없는 경로로 요청보낸 경우
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"PATH_002","METHOD NOT ALLOWED"), // POST GET방식 잘못 보낸경우
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "PATH_003", "UNSUPPORTED MEDIA TYPE"),

    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_001", " AUTHENTICATION_FAILED."),
    LOGIN_FAILED(HttpStatus.NOT_FOUND, "AUTH_002", " LOGIN_FAILED."),
    KNU_READ_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "AUTH_003", "KNU 서버가 응답이 없습니다"), // 강남대서버가 응답이 없을 때
    REGISTER_FAILED(HttpStatus.FORBIDDEN, "AUTH_003", "REGISTER_FAILED"),
    REQUEST_PARAMETER_BIND_FAILED(HttpStatus.BAD_REQUEST, "REQ_001", "PARAMETER_BIND_FAILED"),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH004", "INVALID_JWT_TOKEN."),
    KNU_API_FAILED(HttpStatus.FORBIDDEN, "AUTH_005", "knu api call failed"),
    RESTAURANT_NAME_DUPLICATED(HttpStatus.FORBIDDEN, "RESTAURANT_001", "식당 이름 중복됨"),
    NOT_FOUND_RESTAURANT(HttpStatus.NOT_FOUND, "RESTAURANT_002", "식당이 존재하지 않음"),
    INVALID_RESTAURANT_NAME(HttpStatus.BAD_REQUEST, "RESTAURANT_003", "식당 이름은 공백이 될 수 없습니다."),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "MENU_001", "식당, 날짜, 타입이 같은 메뉴가 존재하지 않음"),
    STATION_NAME_DUPLICATED(HttpStatus.FORBIDDEN, "SHUTTLE_001", "정거장 이름 중복됨"),
    NOT_FOUND_STATION(HttpStatus.NOT_FOUND,"SHUTTLE_002","정거장이 없음."),
    STATION_TIME_DUPLICATED(HttpStatus.FORBIDDEN, "SHUTTLE_003", "정거장 시간이 중복됨"),
    NOT_FOUND_STATION_TIMETABLE(HttpStatus.NOT_FOUND, "SHUTTLE_004", "정거장 시간이 없음"),
    NOT_FOUND_DAY(HttpStatus.NOT_FOUND,"SHUTTLE_005","해당 시간표 요일이 없음"),
    NOT_FOUND_MAP_MARKER(HttpStatus.NOT_FOUND, "MAP_MARKER_001", "마커가 존재하지 않음"),

    FCM_CLIENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FCM_001", "FCM Failed"),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
