package ru.sberdyshev.lesson1.hospital;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("patient")
@PropertySource("application.yaml")
public class Patient {
    @Value("${patient_name}")
    private String name;
}
