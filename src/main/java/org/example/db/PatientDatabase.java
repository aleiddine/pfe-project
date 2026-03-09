package org.example.db;

import org.example.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDatabase {
    private static List<Patient> patients = new ArrayList<>();

    static {
        // Add a mock patient for testing
        patients.add(new Patient("p1", "John Doe", "john@example.com", "password123"));
    }

    public static void addPatient(Patient patient) {
        patients.add(patient);
    }

    public static Patient getPatientByEmailAndPassword(String email, String password) {
        for (Patient p : patients) {
            if (p.getEmail().equals(email) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    public static List<Patient> getAllPatients() {
        return patients;
    }
}
