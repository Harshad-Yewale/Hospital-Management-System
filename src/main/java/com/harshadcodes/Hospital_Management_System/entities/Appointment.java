package com.harshadcodes.Hospital_Management_System.entities;

import com.harshadcodes.Hospital_Management_System.constants.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString(exclude = {"patient","doctor"})
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @NotBlank
    @Column(nullable = false,length = 500)
    private String reason;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;

    public Appointment(LocalDateTime appointmentTime, String reason, AppointmentStatus status) {
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }
}
