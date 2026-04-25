package org.example.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilitaireJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appointmentPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void arreter() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
