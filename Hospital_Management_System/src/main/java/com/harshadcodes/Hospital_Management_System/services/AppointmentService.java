package com.harshadcodes.Hospital_Management_System.services;


import com.harshadcodes.Hospital_Management_System.payload.AppointmentRequest;
import com.harshadcodes.Hospital_Management_System.payload.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAllAppointments(long patient_id);

    AppointmentResponse updateAppointment(long appointment_id, AppointmentRequest updatedAppointment);

    AppointmentResponse deleteAppointment(long appointment_id);

    AppointmentResponse completeAppointment(long appointment_id);



}
