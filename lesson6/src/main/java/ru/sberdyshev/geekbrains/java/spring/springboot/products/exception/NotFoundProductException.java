package ru.sberdyshev.geekbrains.java.spring.springboot.products.exception;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundObjectException;


@Getter
public class NotFoundProductException extends NotFoundObjectException {


    public NotFoundProductException(NotFoundCodes code, String message) {
        super(code, message);
    }
}
