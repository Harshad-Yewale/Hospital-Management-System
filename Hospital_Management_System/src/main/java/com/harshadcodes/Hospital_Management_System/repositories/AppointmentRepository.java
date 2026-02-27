package com.harshadcodes.Hospital_Management_System.repositories;

import com.harshadcodes.Hospital_Management_System.entities.Appointment;
import com.harshadcodes.Hospital_Management_System.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    boolean existsByDoctorAndAppointmentTime(Doctor doctor, @NotNull LocalDateTime appointmentTime);

    List<Appointment> findByPatientId(long patientId);

    boolean existsByDoctorAndAppointmentTimeAndIdNot(Doctor doctor, @NotNull LocalDateTime appointmentTime,Long id);
}
