package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid;

import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.ErrorCode;

public enum NotValidCodes implements ErrorCode {
    NOT_VALID_OBJECT_IS_NULL,
    NOT_VALID_OBJECT_FIELDS_ARE_NULL,
    NOT_VALID_OBJECT_ALREADY_EXISTS;

    @Override
    public String getErrorCode() {
        return this.toString();
    }
}
