package ru.sberdyshev.lesson1.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberdyshev.lesson1.hospital.doctor.Doctor;

@Component("hospital")
public class Hospital {
    @Autowired
    private Doctor availableDoctor;

    public void treat(Patient patient) {
        availableDoctor.treat(patient);
    }
}
