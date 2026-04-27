package org.example.servlet;

import org.example.model.RendezVous;
import org.example.model.Patient;
import org.example.model.SessionInscription;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/reserverRendezVous")
public class RendezVousServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient patient = (Patient) req.getSession().getAttribute("user");
        if (patient == null) {
            resp.sendRedirect(req.getContextPath() + "/connexion_patient.html");
            return;
        }

        String sessionIdStr = req.getParameter("slotId");
        if (sessionIdStr == null || sessionIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/rechercheMedecins");
            return;
        }

        Long sessionId = Long.parseLong(sessionIdStr);
        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        RendezVous rendezVous = null;

        try {
            tx.begin();
            SessionInscription session = em.find(SessionInscription.class, sessionId);
            if (session == null) {
                resp.sendRedirect(req.getContextPath() + "/rechercheMedecins?error=notfound");
                return;
            }

            Patient attachedPatient = em.find(Patient.class, patient.getId());
            
            // Calcul de l'heure du rendez-vous : Heure de début de session + 1 heure
            LocalDateTime heureAjustee = session.getDateDebut().plusHours(1);

            // Création du rendez-vous
            rendezVous = new RendezVous(
                    session.getMedecin(),
                    attachedPatient,
                    heureAjustee,
                    "CONFIRME"
            );
            
            em.persist(rendezVous);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ServletException(e);
        } finally {
            em.close();
        }

        // Redirection vers une page de résumé
        req.setAttribute("appointment", rendezVous);
        req.getRequestDispatcher("/booking_summary.jsp").forward(req, resp);
    }
}
