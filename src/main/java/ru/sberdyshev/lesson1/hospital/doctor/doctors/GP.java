package ru.sberdyshev.lesson1.hospital.doctor.doctors;

import lombok.Getter;
import lombok.Setter;
import ru.sberdyshev.lesson1.hospital.doctor.DoctorImpl;
import ru.sberdyshev.lesson1.hospital.doctor.DoctorType;

@Getter
@Setter
public class GP extends DoctorImpl {
    public GP() {
        this.setType(DoctorType.GP);
    }
}
