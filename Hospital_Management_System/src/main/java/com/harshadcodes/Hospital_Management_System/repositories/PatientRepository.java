package com.harshadcodes.Hospital_Management_System.repositories;
import com.harshadcodes.Hospital_Management_System.entities.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);

    Optional<Patient> findByEmail(String email);
}
