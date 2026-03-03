package com.harshadcodes.Hospital_Management_System.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String departmentName;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;



    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private List<Doctor> doctors=new ArrayList<>();

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
