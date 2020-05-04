package ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.product;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.NotValidCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid.NotValidObjectException;

@Getter
public class NotValidProductException extends NotValidObjectException {


    public NotValidProductException(NotValidCodes code, String message) {
        super(code, message);
    }
}
