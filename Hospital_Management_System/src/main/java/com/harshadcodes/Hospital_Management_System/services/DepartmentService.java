package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.payload.DepartmentCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DepartmentService {
    // CREATE
    @Transactional
    DepartmentResponse createDepartment(DepartmentCreateRequest request);

    // GET BY ID
    @Transactional
    DepartmentResponse getDepartmentById(Long id);

    // GET ALL
    @Transactional
    List<DepartmentResponse> getAllDepartments();

    // UPDATE
    @Transactional
    DepartmentResponse updateDepartment(Long id, DepartmentCreateRequest request);

    // DELETE
    @Transactional
    void deleteDepartment(Long id);

    // ASSIGN DOCTOR
    @Transactional
    void assignDoctorToDepartment(Long departmentId, Long doctorId);

    // REMOVE DOCTOR
    @Transactional
    void removeDoctorFromDepartment(Long departmentId, Long doctorId);
}
