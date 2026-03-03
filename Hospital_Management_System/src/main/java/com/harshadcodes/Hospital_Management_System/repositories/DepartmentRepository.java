package com.harshadcodes.Hospital_Management_System.repositories;

import com.harshadcodes.Hospital_Management_System.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByDepartmentName(String s);
}
