package org.example.util;

import org.example.model.Medecin;
import org.example.model.Patient;
import org.example.model.SessionInscription;
import org.example.model.Administrateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitialisationDonnees implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = UtilitaireJPA.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Long medecinCount = em.createQuery("SELECT COUNT(m) FROM Medecin m", Long.class).getSingleResult();
            if (medecinCount == 0) {
                tx.begin();

                // Ajout de médecins (données initiales sans sessions pré-définies)
                Medecin m1 = new Medecin("Dr. Ahmed Ben Salah", "Cardiologie", "ahmed@nocortisol.com", "pass123", "tunis-ariana");
                Medecin m2 = new Medecin("Dr. Sarah Mansour", "Pédiatrie", "sarah@nocortisol.com", "pass123", "tunis-hay ennaser");
                Medecin m3 = new Medecin("Dr. Anwer ben hamouda", "Médecine Générale", "Anwer@hospital.com", "pass123", "manoube-jdaida");

                em.persist(m1);
                em.persist(m2);
                em.persist(m3);

                // Ajout de patients de test
                em.persist(new Patient("Alice Wonder", "alice@test.com", "pass123"));
                em.persist(new Patient("Bob Miller", "bob@test.com", "pass123"));

                // Ajout de l'administrateur
                em.persist(new Administrateur("Admin", "admin@nocortisol.fr", "admin123"));

                tx.commit();
                System.out.println("Initialisation des données terminée (Modèle Français). Les médecins doivent créer leurs sessions.");
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        UtilitaireJPA.arreter();
    }
}
