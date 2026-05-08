package com.forage.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statutDemande")
public class StatutDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dateStatut;

    @ManyToOne
    @JoinColumn(name = "idDemande")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "idStatut")
    private Statut statut;

    public StatutDemande(){}

    public StatutDemande(LocalDateTime dateStatut, Demande demande, Statut statut){
        this.dateStatut = dateStatut;
        this.demande = demande;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDateStatut() { return dateStatut; }
    public void setDateStatut(LocalDateTime dateStatut) { this.dateStatut = dateStatut; }

    public Demande getDemande() { return demande; }
    public void setDemande(Demande demande) { this.demande = demande; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
}