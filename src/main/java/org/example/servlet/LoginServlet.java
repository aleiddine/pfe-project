package org.example.servlet;

import org.example.db.DoctorDatabase;
import org.example.db.PatientDatabase;
import org.example.model.Doctor;
import org.example.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        resp.setContentType("text/plain");

        boolean isAuthenticated = false;
        String userName = "";

        if ("doctor".equalsIgnoreCase(role)) {
            Doctor d = DoctorDatabase.getDoctorByEmailAndPassword(email, password);
            if (d != null) {
                isAuthenticated = true;
                userName = "Dr. " + d.getName();
            }
        } else {
            Patient p = PatientDatabase.getPatientByEmailAndPassword(email, password);
            if (p != null) {
                isAuthenticated = true;
                userName = p.getName();
            }
        }

        PrintWriter out = resp.getWriter();
        if (isAuthenticated) {
            resp.setStatus(HttpServletResponse.SC_OK);
            out.println("Login Successful!");
            out.println("Welcome back, " + userName + "!");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("Invalid credentials!");
        }
    }
}
