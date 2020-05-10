package ru.sberdyshev.geekbrains.java.spring.springboot.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private String code;
    private String message;
    private StackTraceElement [] details;
}
