package com.harshadcodes.Hospital_Management_System.services;


import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotFoundException;
import com.harshadcodes.Hospital_Management_System.Exceptions.ResourceNotValidException;
import com.harshadcodes.Hospital_Management_System.constants.AppointmentStatus;
import com.harshadcodes.Hospital_Management_System.entities.Appointment;
import com.harshadcodes.Hospital_Management_System.entities.Doctor;
import com.harshadcodes.Hospital_Management_System.entities.Patient;
import com.harshadcodes.Hospital_Management_System.payload.AppointmentRequest;
import com.harshadcodes.Hospital_Management_System.payload.AppointmentResponse;
import com.harshadcodes.Hospital_Management_System.repositories.AppointmentRepository;
import com.harshadcodes.Hospital_Management_System.repositories.DoctorRepository;
import com.harshadcodes.Hospital_Management_System.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Patient patient=patientRepository
                .findById(request.getPatientId()).orElseThrow(()->new ResourceNotFoundException("patient not found with patient_id :"+request.getPatientId()));
        Doctor doctor=doctorRepository
                .findById(request.getDoctorId()).orElseThrow(()->new ResourceNotFoundException("Doctor not found with doctor_id :"+request.getDoctorId()));
        if (appointmentRepository.existsByDoctorAndAppointmentTime(doctor, request.getAppointmentTime())) {
            throw new ResourceNotValidException("Doctor is already booked at this time");
        }
        if (request.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new ResourceNotValidException("Appointment time must be in the future");
        }
        Appointment appointment=new Appointment(
                request.getAppointmentTime(),
                request.getReason(),
                AppointmentStatus.SCHEDULED);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment saved=appointmentRepository.save(appointment);

        return mapToResponse(saved);
    }

    @Override
    public List<AppointmentResponse> getAllAppointments(long patient_id) {

        if(!patientRepository.existsById(patient_id)){
            throw new ResourceNotFoundException("patient not found with patient_id :"+patient_id);
        }

        List<Appointment> appointmentList=appointmentRepository.findByPatientId(patient_id);

        return appointmentList.stream().map(
                appointment ->mapToResponse(appointment)
                ).toList();
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(long appointment_id, AppointmentRequest updatedAppointment)  {

        Appointment appointment=appointmentRepository.findById(appointment_id).orElseThrow(()->
                new ResourceNotFoundException("Appointment not found with id: "+appointment_id));

        if(appointment.getStatus()==AppointmentStatus.COMPLETED || appointment.getStatus()==AppointmentStatus.CANCELLED){
            throw new ResourceNotValidException("cannot update the status of "+appointment.getStatus().name()+" appointment ");
        }
        if (updatedAppointment.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new ResourceNotValidException("Appointment time must be in the future");
        }
        if(appointmentRepository.existsByDoctorAndAppointmentTimeAndIdNot(appointment.getDoctor(),updatedAppointment.getAppointmentTime(),appointment.getId())){
            throw new ResourceNotValidException("doctor is already booked");
        }
        appointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        appointment.setReason(updatedAppointment.getReason());

        return mapToResponse(appointment);
    }

    @Transactional
    @Override
    public AppointmentResponse deleteAppointment(long appointment_id) {
        Appointment appointment=appointmentRepository.findById(appointment_id).orElseThrow(
                ()->new ResourceNotFoundException("Appointment not found with id:"+appointment_id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new ResourceNotValidException(
                    "Only scheduled appointments can be deleted"
            );
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return mapToResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse completeAppointment(long appointment_id) {
        Appointment appointment=appointmentRepository.findById(appointment_id).orElseThrow(
                ()->new ResourceNotFoundException("Appointment not found with id: "+appointment_id)
        );

        if(appointment.getStatus()!=AppointmentStatus.SCHEDULED){
            throw new ResourceNotValidException("Only scheduled appointments can be completed");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        return mapToResponse(appointment);
    }


    //to map appointment to response
    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getPatientName(),
                appointment.getDoctor().getDoctorName(),
                appointment.getAppointmentTime(),
                appointment.getStatus().name()
        );
    }

}
