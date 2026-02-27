package com.harshadcodes.Hospital_Management_System.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,length = 50,unique = true)
    private String policyNumber;

    @NotBlank
    @Column(nullable = false,length = 30)
    private String provider;


    @NotNull
    @Column(nullable = false)
    private LocalDate validUntil;

    @OneToMany(mappedBy = "insurance", fetch = FetchType.LAZY)
    private List<Patient> patients=new ArrayList<>();

    public Insurance(String policyNumber, String provider, LocalDate validUntil) {
        this.policyNumber = policyNumber;
        this.provider = provider;
        this.validUntil = validUntil;
    }
}
