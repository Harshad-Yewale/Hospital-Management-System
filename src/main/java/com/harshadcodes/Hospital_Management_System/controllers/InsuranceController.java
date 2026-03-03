package com.harshadcodes.Hospital_Management_System.controllers;

import com.harshadcodes.Hospital_Management_System.payload.InsuranceCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.InsuranceResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;
import com.harshadcodes.Hospital_Management_System.services.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceResponse> create(
            @Valid @RequestBody InsuranceCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(insuranceService.createInsurance(request));
    }

    @GetMapping("/{id}")
    public InsuranceResponse getById(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id);
    }

    @GetMapping
    public List<InsuranceResponse> getAll() {
        return insuranceService.getAllInsurance();
    }

    @PutMapping("/{id}")
    public InsuranceResponse update(
            @PathVariable Long id,
            @Valid @RequestBody InsuranceCreateRequest request) {

        return insuranceService.updateInsurance(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/patients")
    public List<PatientResponse> getPatients(
            @PathVariable Long id) {

        return insuranceService.getPatientsByInsurance(id);
    }
}
