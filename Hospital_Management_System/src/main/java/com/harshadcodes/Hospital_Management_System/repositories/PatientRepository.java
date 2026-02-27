package com.harshadcodes.Hospital_Management_System.repositories;
import com.harshadcodes.Hospital_Management_System.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}
