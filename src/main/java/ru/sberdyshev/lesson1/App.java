package ru.sberdyshev.lesson1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberdyshev.lesson1.config.AppConfig;
import ru.sberdyshev.lesson1.hospital.Hospital;
import ru.sberdyshev.lesson1.hospital.Patient;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Patient patient = context.getBean("patient", Patient.class);
        Hospital hospital = context.getBean("hospital", Hospital.class);
        hospital.treat(patient);
    }
}
