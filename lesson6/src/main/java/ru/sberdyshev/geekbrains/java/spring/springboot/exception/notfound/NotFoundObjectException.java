package ru.sberdyshev.geekbrains.java.spring.springboot.exception.notfound;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundObjectException extends RuntimeException {
    private NotFoundCodes code;
    private String message;
}
