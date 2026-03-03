package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.payload.PatientCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientSummeryResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PatientService {

    PatientResponse createPatient(PatientCreateRequest patient);

    @Transactional
    PatientSummeryResponse getPatientByEmail(String email);

    @Transactional
    List<PatientResponse> getAllPatients();

    @Transactional
    PatientResponse updatePatient(Long id, PatientCreateRequest request);

    @Transactional
    void deletePatient(Long id);

    @Transactional
    PatientResponse assignInsurance(Long patientId, Long insuranceId);

    @Transactional
    PatientResponse removeInsurance(Long patientId);
}
