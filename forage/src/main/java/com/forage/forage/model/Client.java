package com.forage.forage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(length = 20)
    private String nom;

    @Column(length = 50)
    private String contact;

    @Column(length = 50)
    private String adresse;

    public Client(){
    }

    public Client(String nom , String contact , String adresse){
        this.nom = nom ;
        this.contact = contact ;
        this.adresse = adresse ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "Client{id=" + id + ", nom=" + nom + ", contact=" + contact + ", adresse=" + adresse + "}";
    }
}
