package org.example.servlet;

import org.example.model.RendezVous;
import org.example.model.Medecin;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/medecin/sessions")
public class SessionMedecinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Medecin medecin = (Medecin) req.getSession().getAttribute("user");
        if (medecin == null) {
            resp.sendRedirect(req.getContextPath() + "/connexion_medecin.html");
            return;
        }

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        try {
            // Récupérer les sessions d'inscription
            List<SessionInscription> sessions = em.createQuery("SELECT s FROM SessionInscription s WHERE s.medecin.id = :docId ORDER BY s.dateDebut DESC", SessionInscription.class)
                    .setParameter("docId", medecin.getId())
                    .getResultList();
            
            // Récupérer les rendez-vous réservés
            List<RendezVous> rendezVous = em.createQuery("SELECT r FROM RendezVous r WHERE r.medecin.id = :docId ORDER BY r.dateRendezVous DESC", RendezVous.class)
                    .setParameter("docId", medecin.getId())
                    .getResultList();
            
            req.setAttribute("slots", sessions);
            req.setAttribute("appointments", rendezVous);
            
            req.getRequestDispatcher("/doctor_home.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Medecin medecin = (Medecin) req.getSession().getAttribute("user");
        if (medecin == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String dateDebutStr = req.getParameter("startTime");
        String dateFinStr = req.getParameter("endTime");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateDebut = LocalDateTime.parse(dateDebutStr, formatter);
        LocalDateTime dateFin = LocalDateTime.parse(dateFinStr, formatter);

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Medecin medecinAttache = em.find(Medecin.class, medecin.getId());
            SessionInscription session = new SessionInscription(medecinAttache, dateDebut, dateFin);
            em.persist(session);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ServletException(e);
        } finally {
            em.close();
        }

        resp.sendRedirect(req.getContextPath() + "/medecin/sessions");
    }
}
