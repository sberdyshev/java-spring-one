package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.ErrorCode;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.GenericException;

@Getter
public class NotFoundObjectException extends GenericException {
    public NotFoundObjectException(ErrorCode code, String message) {
        super(code, message);
    }
}
