package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.payload.InsuranceCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.InsuranceResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;

import java.util.List;

public interface InsuranceService {

    InsuranceResponse createInsurance(InsuranceCreateRequest request);

    InsuranceResponse getInsuranceById(Long id);

    List<InsuranceResponse> getAllInsurance();

    InsuranceResponse updateInsurance(Long id, InsuranceCreateRequest request);

    void deleteInsurance(Long id);

    List<PatientResponse> getPatientsByInsurance(Long insuranceId);
}
