package ru.sberdyshev.geekbrains.java.spring.springboot.products.exception;

import lombok.Getter;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidObjectException;

@Getter
public class NotValidProductException extends NotValidObjectException {


    public NotValidProductException(NotValidCodes code, String message) {
        super(code, message);
    }
}
