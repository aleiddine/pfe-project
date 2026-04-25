package org.example.servlet;

import org.example.model.RendezVous;
import org.example.model.Patient;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/patient/rendezvous")
public class ProfilPatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient patient = (Patient) req.getSession().getAttribute("user");
        if (patient == null) {
            resp.sendRedirect(req.getContextPath() + "/connexion_patient.html");
            return;
        }

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        try {
            List<RendezVous> rendezVous = em.createQuery("SELECT r FROM RendezVous r WHERE r.patient.id = :patientId ORDER BY r.dateRendezVous DESC", RendezVous.class)
                    .setParameter("patientId", patient.getId())
                    .getResultList();

            req.setAttribute("appointments", rendezVous);
            req.getRequestDispatcher("/patient_appointments.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }
}
