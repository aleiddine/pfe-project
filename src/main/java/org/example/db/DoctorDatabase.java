package org.example.db;

import org.example.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorDatabase {
    private static List<Doctor> doctors = new ArrayList<>();

    static {
        // Add a mock doctor for testing
        doctors.add(new Doctor("d1", "Dr. Smith", "Cardiology", "smith@hospital.com", "123456"));
    }

    public static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public static Doctor getDoctorByEmailAndPassword(String email, String password) {
        for (Doctor d : doctors) {
            if (d.getEmail().equals(email) && d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;
    }

    public static List<Doctor> getAllDoctors() {
        return doctors;
    }
}
