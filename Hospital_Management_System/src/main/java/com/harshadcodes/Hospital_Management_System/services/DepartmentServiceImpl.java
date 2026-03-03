package com.harshadcodes.Hospital_Management_System.services;

import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceAlreadyExistException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotFoundException;
import com.harshadcodes.Hospital_Management_System.entities.Department;
import com.harshadcodes.Hospital_Management_System.entities.Doctor;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentResponse;
import com.harshadcodes.Hospital_Management_System.repositories.DepartmentRepository;
import com.harshadcodes.Hospital_Management_System.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    // CREATE
    @Transactional
    @Override
    public DepartmentResponse createDepartment(DepartmentCreateRequest request) {

        if (departmentRepository.existsByDepartmentName(request.departmentName())) {
            throw new ResourceAlreadyExistException(
                    "Department already exists with name: " + request.departmentName()
            );
        }

        Department department = new Department(request.departmentName());
        Department saved = departmentRepository.save(department);

        return mapToResponse(saved);
    }


    @Transactional
    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        return mapToResponse(department);
    }

    // GET ALL
    @Transactional
    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    @Transactional
    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentCreateRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        department.setDepartmentName(request.departmentName());

        return mapToResponse(department);
    }

    // DELETE
    @Transactional
    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        departmentRepository.delete(department);
    }

    // ASSIGN DOCTOR
    @Transactional
    @Override
    public void assignDoctorToDepartment(Long departmentId, Long doctorId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found")
                );

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found")
                );

        doctor.getDepartments().add(department);  // owning side
    }

    // REMOVE DOCTOR
    @Transactional
    @Override
    public void removeDoctorFromDepartment(Long departmentId, Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found")
                );

        doctor.getDepartments().removeIf(d -> d.getId().equals(departmentId));
    }

    // MAPPER
    private DepartmentResponse mapToResponse(Department department) {

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
}
