package ru.sberdyshev.geekbrains.java.spring.springboot.users.exception;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundObjectException;


@Getter
public class NotFoundUserException extends NotFoundObjectException {


    public NotFoundUserException(NotFoundCodes code, String message) {
        super(code, message);
    }
}
