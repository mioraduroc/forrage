package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "commune")
public class Commune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "idDistrict")
    private District district;

    @OneToMany(mappedBy = "commune")
    private List<Demande> demandes;

    public Commune(){}

    public Commune(String libelle, District district){
        this.libelle = libelle;
        this.district = district;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public List<Demande> getDemandes() { return demandes; }
    public void setDemandes(List<Demande> demandes) { this.demandes = demandes; }
}