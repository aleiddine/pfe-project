package org.example.servlet;
import org.example.model.Medecin;
import org.example.model.SessionInscription;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/voirSessions")
public class VoirSessionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String medecinIdStr = req.getParameter("id");
        if (medecinIdStr == null || medecinIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/rechercheMedecins");
            return;
        }

        try {
            Long medecinId = Long.parseLong(medecinIdStr);
            EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();

            try {
                Medecin medecin = em.find(Medecin.class, medecinId);
                if (medecin == null) {
                    resp.sendRedirect(req.getContextPath() + "/rechercheMedecins");
                    return;
                }

                // Récupérer les sessions d'inscription pour ce médecin
                List<SessionInscription> sessions = em.createQuery("SELECT s FROM SessionInscription s WHERE s.medecin.id = :docId", SessionInscription.class)
                        .setParameter("docId", medecinId)
                        .getResultList();

                req.setAttribute("doctor", medecin); // clé gardée pour compatibilité JSP
                req.setAttribute("slots", sessions);
                req.getRequestDispatcher("/booking_form.jsp").forward(req, resp);
            } finally {
                em.close();
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/rechercheMedecins");
        }
    }
}
