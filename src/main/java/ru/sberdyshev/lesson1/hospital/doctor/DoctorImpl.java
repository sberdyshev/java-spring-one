package ru.sberdyshev.lesson1.hospital.doctor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import ru.sberdyshev.lesson1.hospital.Patient;

@Getter
@Setter
public class DoctorImpl implements Doctor {
    private DoctorType type;
    @Value("Vladimir")
    private String name;

    public void treat(Patient patient) {
        System.out.println("Doctor \"" + type + "\" (class - \"" + getClass().getSimpleName() + "\") with name \"" + name + "\" treated a patient with name \"" + patient.getName() + "\"");
    }
}
