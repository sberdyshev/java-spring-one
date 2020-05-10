package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound;

import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.ErrorCode;

public enum NotFoundCodes implements ErrorCode {
    NOT_FOUND_OBJECT_IS_MISSING;

    @Override
    public String getErrorCode() {
        return this.toString();
    }
}
