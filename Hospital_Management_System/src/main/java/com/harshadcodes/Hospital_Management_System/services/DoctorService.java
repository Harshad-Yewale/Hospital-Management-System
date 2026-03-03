package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.payload.AppointmentResponse;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentResponse;
import com.harshadcodes.Hospital_Management_System.payload.DoctorCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DoctorResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DoctorService {
    // CREATE
    @Transactional
    DoctorResponse createDoctor(DoctorCreateRequest request);

    // GET BY ID
    @Transactional
    DoctorResponse getDoctorById(Long id);

    @Transactional
    List<DepartmentResponse> getDepartmentsByDoctor(Long doctorId);

    // GET ALL
    @Transactional
    List<DoctorResponse> getAllDoctors();

    // UPDATE
    @Transactional
    DoctorResponse updateDoctor(Long id, DoctorCreateRequest request);

    // DELETE WITH REASSIGNMENT
    @Transactional
    void deleteDoctor(Long doctorId, Long replacementDoctorId);
}
