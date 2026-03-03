package com.harshadcodes.Hospital_Management_System.repositories;

import com.harshadcodes.Hospital_Management_System.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    boolean existsByPolicyNumber(String policyNumber);

}