package org.example.servlet;

import org.example.model.RendezVous;
import org.example.model.Medecin;
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

@WebServlet("/admin/tableauDeBord")
public class DashboardAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = (String) req.getSession().getAttribute("role");
        if (!"admin".equalsIgnoreCase(role)) {
            resp.sendRedirect(req.getContextPath() + "/connexion_admin.html");
            return;
        }

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        try {
            List<Medecin> medecins = em.createQuery("SELECT m FROM Medecin m", Medecin.class).getResultList();
            List<Patient> patients = em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
            List<RendezVous> rendezVous = em.createQuery("SELECT r FROM RendezVous r", RendezVous.class).getResultList();

            req.setAttribute("doctors", medecins);
            req.setAttribute("patients", patients);
            req.setAttribute("appointments", rendezVous);
            
            // Statistiques
            req.setAttribute("doctorCount", medecins.size());
            req.setAttribute("patientCount", patients.size());
            req.setAttribute("appointmentCount", rendezVous.size());

            req.getRequestDispatcher("/admin_home.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        String type = req.getParameter("type");

        if ("delete".equalsIgnoreCase(action) && idStr != null) {
            Long id = Long.parseLong(idStr);
            EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
            try {
                em.getTransaction().begin();
                if ("doctor".equalsIgnoreCase(type)) {
                    Medecin m = em.find(Medecin.class, id);
                    if (m != null) em.remove(m);
                } else if ("patient".equalsIgnoreCase(type)) {
                    Patient p = em.find(Patient.class, id);
                    if (p != null) em.remove(p);
                }
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/tableauDeBord");
    }
}
