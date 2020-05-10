package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.ErrorCode;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.GenericException;

@Getter
public class NotValidObjectException extends GenericException {
    public NotValidObjectException(ErrorCode code, String message) {
        super(code, message);
    }
}
