package com.project.back_end.repo;

import com.project.back_end.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findByDoctorIdAndAppointmentTimeAfterAndStatus(Long doctorId, LocalDateTime appointmentTime, String status);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, String status);
    List<Appointment> findByDoctorIdAndAppointmentTimeAndStatus(Long doctorId, LocalDateTime appointmentTime, String status);
}

