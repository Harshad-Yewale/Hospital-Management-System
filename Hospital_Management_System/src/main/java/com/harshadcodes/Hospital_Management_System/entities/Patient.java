package com.harshadcodes.Hospital_Management_System.entities;


import com.harshadcodes.Hospital_Management_System.constants.BloodGroup;
import com.harshadcodes.Hospital_Management_System.constants.Gender;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    private Gender gender;

    @ToString.Exclude
    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 5)
    private BloodGroup bloodGroup;

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
    @ToString.Exclude
    private List<Appointment> appointments=new ArrayList<>();


    public Patient(String patientName, String email, Gender gender, LocalDate birthDate, BloodGroup bloodGroup) {
        this.patientName = patientName;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodGroup=bloodGroup;
    }
}
