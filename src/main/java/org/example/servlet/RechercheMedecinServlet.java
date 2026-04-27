
package org.example.servlet;

import org.example.model.Medecin;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/rechercheMedecins")
public class RechercheMedecinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recherche = req.getParameter("query");
        String specialiteFiltre = req.getParameter("specialty");
        String localisationFiltre = req.getParameter("location");

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        List<Medecin> medecins;

        try {
            // Requête de base pour récupérer tous les médecins
            String jpql = "SELECT m FROM Medecin m WHERE 1=1";
            
            boolean aRecherche = recherche != null && !recherche.trim().isEmpty();
            boolean aSpecialite = specialiteFiltre != null && !specialiteFiltre.trim().isEmpty() && !specialiteFiltre.equalsIgnoreCase("all");
            boolean aLocalisation = localisationFiltre != null && !localisationFiltre.trim().isEmpty();

            if (aRecherche) {
                jpql += " AND (LOWER(m.nom) LIKE LOWER(:query) OR LOWER(m.specialite) LIKE LOWER(:query))";
            }
            if (aSpecialite) {
                jpql += " AND m.specialite = :speciality";
            }
            if (aLocalisation) {
                jpql += " AND LOWER(m.localisation) LIKE LOWER(:location)";
            }

            var query = em.createQuery(jpql, Medecin.class);
            
            if (aRecherche) {
                query.setParameter("query", "%" + recherche.trim() + "%");
            }
            if (aSpecialite) {
                query.setParameter("speciality", specialiteFiltre);
            }
            if (aLocalisation) {
                query.setParameter("location", "%" + localisationFiltre.trim() + "%");
            }

            medecins = query.getResultList();
        } finally {
            em.close();
        }

        req.setAttribute("doctors", medecins);
        req.getRequestDispatcher("/patient_home.jsp").forward(req, resp);
    }
}
