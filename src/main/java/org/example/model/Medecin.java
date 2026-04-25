package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "medecins")
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String specialite;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = true)
    private String localisation;

    public Medecin() {
    }

    public Medecin(String nom, String specialite, String email, String motDePasse, String localisation) {
        this.nom = nom;
        this.specialite = specialite;
        this.email = email;
        this.motDePasse = motDePasse;
        this.localisation = localisation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Alias getters pour compatibilite avec les JSP existantes
    public String getName() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getSpecialty() {
        return specialite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getLocation() {
        return localisation;
    }
}
