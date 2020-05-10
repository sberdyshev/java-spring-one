package ru.sberdyshev.geekbrains.java.spring.springboot.users.exception;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidObjectException;

@Getter
public class NotValidUserException extends NotValidObjectException {


    public NotValidUserException(NotValidCodes code, String message) {
        super(code, message);
    }
}
