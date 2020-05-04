package ru.sberdyshev.geekbrains.java.spring.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenericListDto<T> {
    private List<T> objectList;
}
