package com.harshadcodes.Hospital_Management_System.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@ToString(exclude = "appointments")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3,message = "Patient name must be at least 3 characters long")
    @Column(nullable = false,length = 100)
    private String patientName;

    @Email(message = "Enter a Valid email")
    @Column(unique = true,nullable = false,length = 150)
    @NotBlank
    private String email;

    @Column(nullable = false,length = 10)
    private String gender;

    @ToString.Exclude
    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false,length = 5)
    private String bloodGroup;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Appointment> appointments=new ArrayList<>();


    public Patient(String patientName, String email, String gender, LocalDate birthDate, String bloodGroup) {
        this.patientName = patientName;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodGroup=bloodGroup;
    }
}
