package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String libelle;

    @OneToMany(mappedBy = "region")
    private List<District> districts;

    public Region(){}

    public Region(String libelle){
        this.libelle = libelle;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public List<District> getDistricts() { return districts; }
    public void setDistricts(List<District> districts) { this.districts = districts; }
}