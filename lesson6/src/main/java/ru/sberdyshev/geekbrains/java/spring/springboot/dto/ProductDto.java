package ru.sberdyshev.geekbrains.java.spring.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {

    private Long id;


    private String name;


    private String description;


    private BigDecimal cost;
}
