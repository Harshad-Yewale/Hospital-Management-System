package com.harshadcodes.Hospital_Management_System.controllers;

import com.harshadcodes.Hospital_Management_System.payload.DepartmentCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DepartmentResponse;
import com.harshadcodes.Hospital_Management_System.services.DepartmentService;
import com.harshadcodes.Hospital_Management_System.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(
            @Valid @RequestBody DepartmentCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}/departments")
    public List<DepartmentResponse> getDepartments(@PathVariable Long id) {
        return doctorService.getDepartmentsByDoctor(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> update(
            @PathVariable Long id,
            @RequestBody DepartmentCreateRequest request) {

        return ResponseEntity.ok(departmentService.updateDepartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{departmentId}/assign/{doctorId}")
    public ResponseEntity<Void> assign(
            @PathVariable Long departmentId,
            @PathVariable Long doctorId) {

        departmentService.assignDoctorToDepartment(departmentId, doctorId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{departmentId}/remove/{doctorId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long departmentId,
            @PathVariable Long doctorId) {

        departmentService.removeDoctorFromDepartment(departmentId, doctorId);
        return ResponseEntity.ok().build();
    }


}