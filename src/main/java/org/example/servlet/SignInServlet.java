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
import java.util.UUID;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/patientinscription.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String specialty = req.getParameter("specialty");

        String id = UUID.randomUUID().toString();

        if ("doctor".equalsIgnoreCase(role)) {
            Doctor d = new Doctor(id, name, specialty, email, password);
            DoctorDatabase.addDoctor(d);
        } else {
            Patient p = new Patient(id, name, email, password);
            PatientDatabase.addPatient(p);
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        PrintWriter out = resp.getWriter();
        out.println("<html><body style='font-family:sans-serif;text-align:center;margin-top:80px;'>");
        out.println("<h2>&#10003; You are now registered</h2>");
        out.println("<p>Welcome, <strong>" + name + "</strong>! Your account has been created successfully.</p>");
        out.println("</body></html>");
    }
}
