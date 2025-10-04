-- Smart Clinic Database Setup Script
-- This script creates the database and all necessary tables

-- Create database
CREATE DATABASE IF NOT EXISTS smart_clinic_db;
USE smart_clinic_db;

-- Create patients table
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create doctors table
CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create appointments table
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Create prescriptions table
CREATE TABLE prescriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,
    medication VARCHAR(255) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    notes TEXT,
    instructions TEXT,
    duration VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

-- Create tokens table
CREATE TABLE tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry DATETIME NOT NULL,
    user_type ENUM('DOCTOR', 'PATIENT', 'ADMIN') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for performance
CREATE INDEX idx_patients_email ON patients(email);
CREATE INDEX idx_doctors_email ON doctors(email);
CREATE INDEX idx_doctors_specialty ON doctors(specialty);
CREATE INDEX idx_appointments_doctor_date ON appointments(doctor_id, appointment_time);
CREATE INDEX idx_appointments_patient ON appointments(patient_id);
CREATE INDEX idx_appointments_status ON appointments(status);
CREATE INDEX idx_tokens_user_id ON tokens(user_id);
CREATE INDEX idx_tokens_token ON tokens(token);
CREATE INDEX idx_tokens_expiry ON tokens(expiry);

-- Insert sample doctors
INSERT INTO doctors (name, specialty, email, phone, password) VALUES
('Dr. John Smith', 'Cardiology', 'john.smith@clinic.com', '555-0101', 'password123'),
('Dr. Sarah Johnson', 'Dermatology', 'sarah.johnson@clinic.com', '555-0102', 'password123'),
('Dr. Michael Brown', 'Neurology', 'michael.brown@clinic.com', '555-0103', 'password123'),
('Dr. Emily Davis', 'Pediatrics', 'emily.davis@clinic.com', '555-0104', 'password123'),
('Dr. Robert Wilson', 'Orthopedics', 'robert.wilson@clinic.com', '555-0105', 'password123');

-- Insert sample patients
INSERT INTO patients (name, email, phone, password, address, date_of_birth) VALUES
('Alice Johnson', 'alice.johnson@email.com', '555-1001', 'password123', '123 Main St', '1990-05-15'),
('Bob Smith', 'bob.smith@email.com', '555-1002', 'password123', '456 Oak Ave', '1985-08-22'),
('Carol Davis', 'carol.davis@email.com', '555-1003', 'password123', '789 Pine Rd', '1992-03-10'),
('David Wilson', 'david.wilson@email.com', '555-1004', 'password123', '321 Elm St', '1988-11-05'),
('Eva Brown', 'eva.brown@email.com', '555-1005', 'password123', '654 Maple Dr', '1995-07-18');

-- Insert sample appointments
INSERT INTO appointments (patient_id, doctor_id, appointment_time, status, notes) VALUES
(1, 1, '2024-01-15 09:00:00', 'COMPLETED', 'Regular checkup'),
(2, 2, '2024-01-16 10:30:00', 'SCHEDULED', 'Skin examination'),
(3, 1, '2024-01-17 14:00:00', 'SCHEDULED', 'Heart consultation'),
(4, 3, '2024-01-18 11:00:00', 'SCHEDULED', 'Neurological assessment'),
(5, 4, '2024-01-19 15:30:00', 'SCHEDULED', 'Pediatric consultation');

-- Insert sample prescriptions
INSERT INTO prescriptions (appointment_id, medication, dosage, notes, instructions, duration) VALUES
(1, 'Lisinopril', '10mg', 'For blood pressure control', 'Take once daily with food', '30 days'),
(2, 'Hydrocortisone Cream', '1%', 'For skin irritation', 'Apply twice daily to affected area', '14 days');

-- Create stored procedures
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN doctor_id_param BIGINT,
    IN report_date DATE
)
BEGIN
    SELECT 
        d.name as doctor_name,
        p.name as patient_name,
        p.phone as patient_phone,
        a.appointment_time,
        a.status
    FROM appointments a
    JOIN doctors d ON a.doctor_id = d.id
    JOIN patients p ON a.patient_id = p.id
    WHERE a.doctor_id = doctor_id_param
    AND DATE(a.appointment_time) = report_date
    ORDER BY d.name, a.appointment_time;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN month_param INT,
    IN year_param INT
)
BEGIN
    SELECT 
        d.id as doctor_id,
        COUNT(DISTINCT a.patient_id) as patients_seen
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE MONTH(a.appointment_time) = month_param
    AND YEAR(a.appointment_time) = year_param
    GROUP BY d.id
    ORDER BY patients_seen DESC
    LIMIT 1;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN year_param INT
)
BEGIN
    SELECT 
        d.id as doctor_id,
        COUNT(DISTINCT a.patient_id) as patients_seen
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = year_param
    GROUP BY d.id
    ORDER BY patients_seen DESC
    LIMIT 1;
END //
DELIMITER ;
