package com.forage.forage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "statutDemande")
public class StatutDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idDemande")
    private Demande demande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idStatut")
    private Statut statut;

    private LocalDateTime dateStatut;

    public StatutDemande() {
    }

    public StatutDemande(Demande demande, Statut statut, LocalDateTime dateStatut) {
        this.demande = demande;
        this.statut = statut;
        this.dateStatut = dateStatut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateStatut() {
        return dateStatut;
    }

    public void setDateStatut(LocalDateTime dateStatut) {
        this.dateStatut = dateStatut;
    }

    @Override
    public String toString() {
        return "StatutDemande{" +
                "id=" + id +
                ", demande=" + (demande != null ? demande.getId() : null) +
                ", statut=" + (statut != null ? statut.getLibelle() : null) +
                ", date=" + dateStatut +
                '}';
    }
}
