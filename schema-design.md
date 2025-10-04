# Smart Clinic Database Design

## Overview
This document describes the MySQL database design for the Smart Clinic Management System, a comprehensive healthcare management platform.

## Database Schema

### 1. Patients Table
```sql
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
```

**Data Types:**
- `id`: BIGINT (Primary Key, Auto Increment)
- `name`: VARCHAR(100) (Patient's full name)
- `email`: VARCHAR(100) (Unique email address)
- `password`: VARCHAR(255) (Hashed password)
- `phone`: VARCHAR(20) (Contact phone number)
- `address`: VARCHAR(255) (Patient's address)
- `date_of_birth`: DATE (Patient's birth date)
- `created_at`: TIMESTAMP (Record creation time)
- `updated_at`: TIMESTAMP (Last update time)

### 2. Doctors Table
```sql
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
```

### 3. Appointments Table
```sql
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
```

### 4. Prescriptions Table
```sql
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
```

### 5. Tokens Table
```sql
CREATE TABLE tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry DATETIME NOT NULL,
    user_type ENUM('DOCTOR', 'PATIENT', 'ADMIN') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Stored Procedures

### 1. GetDailyAppointmentReportByDoctor
```sql
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN doctor_id_param BIGINT,
    IN report_date DATE
)
BEGIN
    SELECT 
        d.name as doctor_name,
        p.name as patient_name,
        a.appointment_time,
        a.status,
        a.notes
    FROM appointments a
    JOIN doctors d ON a.doctor_id = d.id
    JOIN patients p ON a.patient_id = p.id
    WHERE a.doctor_id = doctor_id_param
    AND DATE(a.appointment_time) = report_date
    ORDER BY a.appointment_time;
END //
DELIMITER ;
```

### 2. GetDoctorWithMostPatientsByMonth
```sql
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN month_param INT,
    IN year_param INT
)
BEGIN
    SELECT 
        d.id,
        d.name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) as patient_count
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE MONTH(a.appointment_time) = month_param
    AND YEAR(a.appointment_time) = year_param
    GROUP BY d.id, d.name, d.specialty
    ORDER BY patient_count DESC
    LIMIT 1;
END //
DELIMITER ;
```

### 3. GetDoctorWithMostPatientsByYear
```sql
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN year_param INT
)
BEGIN
    SELECT 
        d.id,
        d.name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) as patient_count
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = year_param
    GROUP BY d.id, d.name, d.specialty
    ORDER BY patient_count DESC
    LIMIT 1;
END //
DELIMITER ;
```

## Indexes

```sql
-- Performance indexes
CREATE INDEX idx_patients_email ON patients(email);
CREATE INDEX idx_doctors_email ON doctors(email);
CREATE INDEX idx_doctors_specialty ON doctors(specialty);
CREATE INDEX idx_appointments_doctor_date ON appointments(doctor_id, appointment_time);
CREATE INDEX idx_appointments_patient ON appointments(patient_id);
CREATE INDEX idx_appointments_status ON appointments(status);
CREATE INDEX idx_tokens_user_id ON tokens(user_id);
CREATE INDEX idx_tokens_token ON tokens(token);
CREATE INDEX idx_tokens_expiry ON tokens(expiry);
```

## Sample Data

### Insert Sample Doctors
```sql
INSERT INTO doctors (name, specialty, email, phone, password) VALUES
('Dr. John Smith', 'Cardiology', 'john.smith@clinic.com', '555-0101', 'password123'),
('Dr. Sarah Johnson', 'Dermatology', 'sarah.johnson@clinic.com', '555-0102', 'password123'),
('Dr. Michael Brown', 'Neurology', 'michael.brown@clinic.com', '555-0103', 'password123'),
('Dr. Emily Davis', 'Pediatrics', 'emily.davis@clinic.com', '555-0104', 'password123'),
('Dr. Robert Wilson', 'Orthopedics', 'robert.wilson@clinic.com', '555-0105', 'password123');
```

### Insert Sample Patients
```sql
INSERT INTO patients (name, email, phone, password, address, date_of_birth) VALUES
('Alice Johnson', 'alice.johnson@email.com', '555-1001', 'password123', '123 Main St', '1990-05-15'),
('Bob Smith', 'bob.smith@email.com', '555-1002', 'password123', '456 Oak Ave', '1985-08-22'),
('Carol Davis', 'carol.davis@email.com', '555-1003', 'password123', '789 Pine Rd', '1992-03-10'),
('David Wilson', 'david.wilson@email.com', '555-1004', 'password123', '321 Elm St', '1988-11-05'),
('Eva Brown', 'eva.brown@email.com', '555-1005', 'password123', '654 Maple Dr', '1995-07-18');
```

## Database Relationships

### Foreign Key Relationships:
- **appointments.patient_id** → **patients.id** (One-to-Many: Patient → Appointments)
- **appointments.doctor_id** → **doctors.id** (One-to-Many: Doctor → Appointments)
- **prescriptions.appointment_id** → **appointments.id** (One-to-One: Appointment → Prescription)

### Relationship Details:
- **One-to-Many**: Doctor → Appointments (A doctor can have many appointments)
- **One-to-Many**: Patient → Appointments (A patient can have many appointments)
- **One-to-One**: Appointment → Prescription (Each appointment can have one prescription)
- **One-to-Many**: User → Tokens (for authentication - any user can have multiple tokens)

## Security Considerations

1. **Password Hashing**: All passwords should be hashed using BCrypt
2. **Token Expiration**: Tokens expire after 24 hours
3. **SQL Injection Prevention**: Use parameterized queries
4. **Data Validation**: Implement proper input validation
5. **Access Control**: Role-based access control for different user types