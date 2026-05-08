package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "statut")
public class Statut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, unique = true)
    private String statut;

    @OneToMany(mappedBy = "statut")
    private List<StatutDemande> statutDemandes;

    public Statut(){}

    public Statut(String statut){
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public List<StatutDemande> getStatutDemandes() { return statutDemandes; }
    public void setStatutDemandes(List<StatutDemande> statutDemandes) { this.statutDemandes = statutDemandes; }
}