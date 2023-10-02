package com.allknu.backend.global.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
