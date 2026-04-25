package org.example.servlet;

import org.example.model.Medecin;
import org.example.model.Patient;
import org.example.model.Administrateur;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("password"); // On garde les noms de paramètres HTTP pour le moment si les formulaires ne sont pas encore renommés
        String role = req.getParameter("role");

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        Object utilisateur = null;

        try {
            if ("doctor".equalsIgnoreCase(role)) {
                utilisateur = em.createQuery("SELECT m FROM Medecin m WHERE m.email = :email AND m.motDePasse = :password", Medecin.class)
                        .setParameter("email", email)
                        .setParameter("password", motDePasse)
                        .getSingleResult();
            } else if ("admin".equalsIgnoreCase(role)) {
                utilisateur = em.createQuery("SELECT ad FROM Administrateur ad WHERE ad.email = :email AND ad.motDePasse = :password", Administrateur.class)
                        .setParameter("email", email)
                        .setParameter("password", motDePasse)
                        .getSingleResult();
            } else {
                utilisateur = em.createQuery("SELECT p FROM Patient p WHERE p.email = :email AND p.motDePasse = :password", Patient.class)
                        .setParameter("email", email)
                        .setParameter("password", motDePasse)
                        .getSingleResult();
            }
        } catch (NoResultException e) {
            utilisateur = null;
        } finally {
            em.close();
        }

        if (utilisateur != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", utilisateur);
            session.setAttribute("role", role);

            if ("doctor".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/medecin/sessions");
            } else if ("admin".equalsIgnoreCase(role)) {
                resp.sendRedirect(req.getContextPath() + "/admin/tableauDeBord");
            } else {
                resp.sendRedirect(req.getContextPath() + "/rechercheMedecins");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/connexion_" + (role != null ? role.toLowerCase() : "patient") + ".html?error=invalid");
        }
    }
}
