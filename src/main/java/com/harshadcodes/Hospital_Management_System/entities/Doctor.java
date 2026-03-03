package com.harshadcodes.Hospital_Management_System.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString(exclude = "appointments")
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String doctorName;

    @Email
    @NotBlank
    @Column(nullable = false,unique = true,length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String specialization;

    @NotNull
    @Column(nullable = false)
    private Double salary;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Appointment> appointments=new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_department",
        joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments=new ArrayList<>();

    public Doctor(String doctorName, String email, String specialization, Double salary) {
        this.doctorName = doctorName;
        this.email = email;
        this.specialization = specialization;
        this.salary = salary;
    }
}
