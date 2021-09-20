package com.allknu.backend.core.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
