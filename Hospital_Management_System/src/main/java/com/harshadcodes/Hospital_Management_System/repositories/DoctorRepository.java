package com.harshadcodes.Hospital_Management_System.repositories;

import com.harshadcodes.Hospital_Management_System.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    boolean existsByEmail(String email);
}
