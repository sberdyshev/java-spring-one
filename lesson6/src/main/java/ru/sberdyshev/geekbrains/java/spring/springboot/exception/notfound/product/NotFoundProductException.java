package ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound.product;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound.NotFoundCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound.NotFoundObjectException;


@Getter
public class NotFoundProductException extends NotFoundObjectException {


    public NotFoundProductException(NotFoundCodes code, String message) {
        super(code, message);
    }
}
