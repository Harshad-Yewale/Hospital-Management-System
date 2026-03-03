package com.harshadcodes.Hospital_Management_System.services;


import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceAlreadyExistException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotFoundException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotValidException;
import com.harshadcodes.Hospital_Management_System.constants.AppointmentStatus;
import com.harshadcodes.Hospital_Management_System.constants.BloodGroup;
import com.harshadcodes.Hospital_Management_System.constants.Gender;
import com.harshadcodes.Hospital_Management_System.entities.Insurance;
import com.harshadcodes.Hospital_Management_System.entities.Patient;
import com.harshadcodes.Hospital_Management_System.payload.PatientCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientSummeryResponse;
import com.harshadcodes.Hospital_Management_System.repositories.AppointmentRepository;
import com.harshadcodes.Hospital_Management_System.repositories.InsuranceRepository;
import com.harshadcodes.Hospital_Management_System.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final InsuranceRepository insuranceRepository;



    @Transactional
    @Override
    public PatientResponse createPatient(PatientCreateRequest request) {
        if(patientRepository.existsByEmail(request.email())){
            throw new ResourceAlreadyExistException("Patient  All ready Exists with Email: "+request.email());
        }
        Gender gender=validGenderChecker(request.gender());
        BloodGroup bloodGroup=validBloodGroupChecker(request.bloodGroup());
        Patient p=new Patient(request.patientName(), request.email(), gender,request.birthDate(),bloodGroup);
        Patient savedPatient=patientRepository.save(p);
        return mapToResponse(savedPatient);
    }

    @Transactional
    @Override
    public PatientSummeryResponse getPatientByEmail(String email) {

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with Email: " + email));
        return new PatientSummeryResponse(
                patient.getId(),
                patient.getPatientName(),
                patient.getEmail(),
                patient.getGender().name()
        );
    }

    @Transactional
    @Override
    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public PatientResponse updatePatient(Long id, PatientCreateRequest request) {

        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id)
                );

        if (!patient.getEmail().equals(request.email()) && patientRepository.existsByEmail(request.email()))
        {
            throw new ResourceAlreadyExistException("Email already in use: " + request.email());
        }
        Gender gender=validGenderChecker(request.gender());
        BloodGroup bloodGroup=validBloodGroupChecker(request.bloodGroup());

        patient.setPatientName(request.patientName());
        patient.setEmail(request.email());
        patient.setGender(gender);
        patient.setBirthDate(request.birthDate());
        patient.setBloodGroup(bloodGroup);

        return mapToResponse(patient);
    }

    @Transactional
    @Override
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id)
                );

        boolean hasScheduledAppointments = appointmentRepository.existsByPatientIdAndStatus(id, AppointmentStatus.SCHEDULED);

        if (hasScheduledAppointments) {
            throw new ResourceNotValidException("Cannot delete patient with scheduled appointments");
        }

        patientRepository.delete(patient);
    }

    @Transactional
    @Override
    public PatientResponse assignInsurance(Long patientId, Long insuranceId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insurance not found")
                );

        if (insurance.getValidUntil().isBefore(LocalDate.now())) {
            throw new ResourceNotValidException("Insurance is expired");
        }

        patient.setInsurance(insurance);

        return mapToResponse(patient);
    }

    @Transactional
    @Override
    public PatientResponse removeInsurance(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );

        patient.setInsurance(null);

        return mapToResponse(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {

        Long insuranceId = patient.getInsurance() != null
                ? patient.getInsurance().getId()
                : null;

        return new PatientResponse(
                patient.getId(),
                patient.getPatientName(),
                patient.getEmail(),
                patient.getGender().name().toLowerCase(),
                patient.getBirthDate(),
                patient.getBloodGroup().name(),
                insuranceId,
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }

    private Gender validGenderChecker(String gender) {

        Gender genderEnum;

        try {
            genderEnum = Gender.valueOf(gender.toUpperCase());
            return genderEnum;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotValidException("Invalid gender value");
        }

    }
    private BloodGroup validBloodGroupChecker(String bloodGroup) {
        BloodGroup bloodGroupEnum;
        try {
            bloodGroupEnum = BloodGroup.valueOf(bloodGroup);
            return bloodGroupEnum;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotValidException("Invalid blood group value");
        }
    }

}
