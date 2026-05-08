package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(length = 20)
    private String nom;

    @Column(length = 50)
    private String contact;

    @Column(length = 50)
    private String adresse;

    // relation avec Demande
    @OneToMany(mappedBy = "client")
    private List<Demande> demandes;

    public Client(){
    }

    public Client(String nom , String contact , String adresse){
        this.nom = nom ;
        this.contact = contact ;
        this.adresse = adresse ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
}