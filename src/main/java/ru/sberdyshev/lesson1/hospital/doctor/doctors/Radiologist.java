package ru.sberdyshev.lesson1.hospital.doctor.doctors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sberdyshev.lesson1.hospital.doctor.DoctorImpl;
import ru.sberdyshev.lesson1.hospital.doctor.DoctorType;
import ru.sberdyshev.lesson1.hospital.doctor.NotAvailableDoctor;

@Getter
@Setter
@Component("doctor")
@NotAvailableDoctor(goToDoctor = GP.class)
public class Radiologist extends DoctorImpl {
    public Radiologist() {
        this.setType(DoctorType.RADIOLOGIST);
    }
}
