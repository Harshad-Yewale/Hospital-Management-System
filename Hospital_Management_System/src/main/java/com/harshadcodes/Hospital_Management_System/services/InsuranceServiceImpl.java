package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceAlreadyExistException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotFoundException;
import com.harshadcodes.Hospital_Management_System.entities.Insurance;
import com.harshadcodes.Hospital_Management_System.entities.Patient;
import com.harshadcodes.Hospital_Management_System.payload.InsuranceCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.InsuranceResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;
import com.harshadcodes.Hospital_Management_System.repositories.InsuranceRepository;
import com.harshadcodes.Hospital_Management_System.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    // CREATE
    @Transactional
    @Override
    public InsuranceResponse createInsurance(InsuranceCreateRequest request) {

        if (insuranceRepository.existsByPolicyNumber(request.policyNumber())) {
            throw new ResourceAlreadyExistException(
                    "Insurance already exists with policy number: " + request.policyNumber()
            );
        }

        Insurance insurance = new Insurance(
                request.policyNumber(),
                request.provider(),
                request.validUntil()
        );

        Insurance saved = insuranceRepository.save(insurance);

        return mapToResponse(saved);
    }

    // GET BY ID
    @Override
    public InsuranceResponse getInsuranceById(Long id) {

        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + id)
                );

        return mapToResponse(insurance);
    }

    // GET ALL
    @Override
    public List<InsuranceResponse> getAllInsurance() {

        return insuranceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    @Transactional
    @Override
    public InsuranceResponse updateInsurance(Long id, InsuranceCreateRequest request) {

        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + id)
                );

        if (!insurance.getPolicyNumber().equals(request.policyNumber())
                && insuranceRepository.existsByPolicyNumber(request.policyNumber())) {

            throw new ResourceAlreadyExistException(
                    "Policy number already exists: " + request.policyNumber()
            );
        }

        insurance.setPolicyNumber(request.policyNumber());
        insurance.setProvider(request.provider());
        insurance.setValidUntil(request.validUntil());

        return mapToResponse(insurance);
    }

    // DELETE
    @Transactional
    @Override
    public void deleteInsurance(Long id) {

        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + id)
                );

        // Business rule: Remove insurance from patients before deleting
        for (Patient patient : insurance.getPatients()) {
            patient.setInsurance(null);
        }

        insuranceRepository.delete(insurance);
    }

    // GET PATIENTS UNDER INSURANCE
    @Override
    public List<PatientResponse> getPatientsByInsurance(Long insuranceId) {

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found with id: " + insuranceId)
                );

        return insurance.getPatients()
                .stream()
                .map(this::mapPatientToResponse)
                .toList();
    }

    // ------------------ MAPPERS ------------------

    private InsuranceResponse mapToResponse(Insurance insurance) {

        List<Long> patientIds = insurance.getPatients()
                .stream()
                .map(Patient::getId)
                .toList();

        return new InsuranceResponse(
                insurance.getId(),
                insurance.getPolicyNumber(),
                insurance.getProvider(),
                insurance.getValidUntil(),
                patientIds
        );
    }

    private PatientResponse mapPatientToResponse(Patient patient) {

        return new PatientResponse(
                patient.getId(),
                patient.getPatientName(),
                patient.getEmail(),
                patient.getGender().name(),
                patient.getBirthDate(),
                patient.getBloodGroup().name(),
                patient.getInsurance() != null ? patient.getInsurance().getId() : null,
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }
}