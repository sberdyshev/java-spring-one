package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericException extends RuntimeException {
    private ErrorCode code;
    private String message;

    @Override
    public String toString() {
        return "Error{" +
                "type='" + this.getClass().getName() + '\'' +
                ",code='" + code.getErrorCode() + '\'' +
                ",string='" + code.toString() + '\'' +
                ",message='" + message + '\'' +
                '}';
    }
}
