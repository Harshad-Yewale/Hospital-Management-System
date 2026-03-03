package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceAlreadyExistException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotFoundException;
import com.harshadcodes.Hospital_Management_System.entities.Appointment;
import com.harshadcodes.Hospital_Management_System.entities.Department;
import com.harshadcodes.Hospital_Management_System.entities.Doctor;
import com.harshadcodes.Hospital_Management_System.payload.AppointmentResponse;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentResponse;
import com.harshadcodes.Hospital_Management_System.payload.DoctorCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DoctorResponse;
import com.harshadcodes.Hospital_Management_System.repositories.AppointmentRepository;
import com.harshadcodes.Hospital_Management_System.repositories.DepartmentRepository;
import com.harshadcodes.Hospital_Management_System.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public DoctorResponse createDoctor(DoctorCreateRequest request) {

        if (doctorRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistException("Doctor already exists with email: " + request.email());
        }

        Doctor doctor = new Doctor(
                request.doctorName(),
                request.email(),
                request.specialization(),
                request.salary()
        );

        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }


    @Transactional
    @Override
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        return mapToResponse(doctor);
    }

    @Transactional
    @Override
    public List<DepartmentResponse> getDepartmentsByDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId)
                );

        return doctor.getDepartments()
                .stream()
                .map(this::mapDepartmentToResponse)
                .toList();
    }
    @Transactional
    @Override
    public List<DoctorResponse> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public DoctorResponse updateDoctor(Long id, DoctorCreateRequest request) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (!doctor.getEmail().equals(request.email()) && doctorRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistException("Email already in use: " + request.email());
        }

        doctor.setDoctorName(request.doctorName());
        doctor.setEmail(request.email());
        doctor.setSpecialization(request.specialization());
        doctor.setSalary(request.salary());

        return mapToResponse(doctor);
    }



    @Transactional
    @Override
    public void deleteDoctor(Long doctorId, Long replacementDoctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        if (doctorId.equals(replacementDoctorId)) {
            throw new IllegalArgumentException("Replacement doctor cannot be the same doctor");
        }

        Doctor replacementDoctor = doctorRepository.findById(replacementDoctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Replacement doctor not found with id: " + replacementDoctorId)
                );

        // Reassign all appointments
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        for (Appointment appointment : appointments) {
            appointment.setDoctor(replacementDoctor);
        }
        doctorRepository.delete(doctor);
    }


    private DoctorResponse mapToResponse(Doctor doctor) {

        return new DoctorResponse(
                doctor.getId(),
                doctor.getDoctorName(),
                doctor.getEmail(),
                doctor.getSpecialization(),
                doctor.getSalary(),
                doctor.getCreatedAt(),
                doctor.getUpdatedAt()
        );
    }

    private DepartmentResponse mapDepartmentToResponse(Department department) {

        List<Long> doctorIds = department.getDoctors()
                .stream()
                .map(Doctor::getId)
                .toList();

        return new DepartmentResponse(
                department.getId(),
                department.getDepartmentName(),
                department.getCreatedAt(),
                doctorIds
        );
    }

    private AppointmentResponse mapAppointmentToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getPatientName(),
                appointment.getDoctor().getDoctorName(),
                appointment.getAppointmentTime(),
                appointment.getStatus().name()
        );
    }
}