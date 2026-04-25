package org.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions_inscription")
public class SessionInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column(nullable = false)
    private boolean estReserve;

    public SessionInscription() {
    }

    public SessionInscription(Medecin medecin, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.medecin = medecin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estReserve = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public boolean estReserve() { return estReserve; }
    public void setEstReserve(boolean estReserve) { this.estReserve = estReserve; }

    // Alias getters pour compatibilite avec les JSP existantes
    public LocalDateTime getStartTime() { return dateDebut; }
    public LocalDateTime getEndTime() { return dateFin; }
    public boolean isBooked() { return estReserve; }
    public boolean getBooked() { return estReserve; }
}
