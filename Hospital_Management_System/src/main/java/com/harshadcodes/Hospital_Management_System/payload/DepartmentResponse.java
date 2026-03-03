package com.harshadcodes.Hospital_Management_System.payload;

import java.time.LocalDateTime;
import java.util.List;

public record DepartmentResponse(
        Long id,
        String departmentName,
        LocalDateTime createdAt,
        List<Long> doctorIds
) {}
