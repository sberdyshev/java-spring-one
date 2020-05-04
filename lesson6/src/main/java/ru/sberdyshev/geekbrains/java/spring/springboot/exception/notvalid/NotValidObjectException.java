package ru.sberdyshev.geekbrains.java.spring.springboot.exception.notvalid;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotValidObjectException extends RuntimeException {
    private NotValidCodes code;
    private String message;
}
