package org.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime dateRendezVous;

    @Column(nullable = false)
    private String statut; // ex: EN_ATTENTE, CONFIRME, ANNULE

    public RendezVous() {
    }

    public RendezVous(Medecin medecin, Patient patient, LocalDateTime dateRendezVous, String statut) {
        this.medecin = medecin;
        this.patient = patient;
        this.dateRendezVous = dateRendezVous;
        this.statut = statut;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDateTime getDateRendezVous() { return dateRendezVous; }
    public void setDateRendezVous(LocalDateTime dateRendezVous) { this.dateRendezVous = dateRendezVous; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    // Alias getters pour compatibilite avec les JSP existantes
    public Medecin getDoctor() { return medecin; }
    public LocalDateTime getAppointmentTime() { return dateRendezVous; }
    public String getStatus() { return statut; }
}
