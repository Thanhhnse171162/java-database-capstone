\# Smart Clinic Database Design



\## Tables

\- Patient(id, name, email, password, phone)

\- Doctor(id, name, specialty, email, phone)

\- Appointment(id, patient\_id, doctor\_id, appointment\_time, status)

\- Prescription(id, appointment\_id, medication, dosage, notes)

\- Token(id, user\_id, token, expiry)



