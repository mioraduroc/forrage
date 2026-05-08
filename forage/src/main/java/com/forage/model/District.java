package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "idRegion")
    private Region region;

    @OneToMany(mappedBy = "district")
    private List<Commune> communes;

    public District(){}

    public District(String libelle, Region region){
        this.libelle = libelle;
        this.region = region;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }

    public List<Commune> getCommunes() { return communes; }
    public void setCommunes(List<Commune> communes) { this.communes = communes; }
}