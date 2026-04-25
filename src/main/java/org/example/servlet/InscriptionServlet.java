package org.example.servlet;

import org.example.model.Medecin;
import org.example.model.Patient;
import org.example.util.UtilitaireJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"/inscription", "/inscription_patient"})
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/inscription_patient.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("name");
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("password");
        String role = req.getParameter("role");
        String specialite = req.getParameter("specialty");
        String localisation = req.getParameter("location");

        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if ("doctor".equalsIgnoreCase(role)) {
                Medecin m = new Medecin(nom, specialite, email, motDePasse, localisation);
                em.persist(m);
            } else {
                Patient p = new Patient(nom, email, motDePasse);
                em.persist(p);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ServletException(e);
        } finally {
            em.close();
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        PrintWriter out = resp.getWriter();
        out.println("<html><body style='font-family:sans-serif;text-align:center;margin-top:80px;'>");
        out.println("<h2>&#10003; Inscription Réussie</h2>");
        out.println("<p>Bienvenue, <strong>" + nom + "</strong> ! Votre compte a été créé avec succès.</p>");
        out.println("<p><a href='index.html'>Cliquez ici pour vous connecter</a></p>");
        out.println("</body></html>");
    }
}
