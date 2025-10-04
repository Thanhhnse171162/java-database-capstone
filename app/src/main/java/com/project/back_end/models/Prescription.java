package com.project.back_end.models;

import javax.persistence.*;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;
    
    @Column(nullable = false)
    private String medication;
    
    @Column(nullable = false)
    private String dosage;
    
    @Column(length = 1000)
    private String notes;
    
    @Column
    private String instructions;
    
    @Column
    private String duration;
    
    // Constructors
    public Prescription() {}
    
    public Prescription(Appointment appointment, String medication, String dosage, String notes) {
        this.appointment = appointment;
        this.medication = medication;
        this.dosage = dosage;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public String getMedication() {
        return medication;
    }
    
    public void setMedication(String medication) {
        this.medication = medication;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", appointment=" + (appointment != null ? appointment.getId() : "null") +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", notes='" + notes + '\'' +
                ", instructions='" + instructions + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}

