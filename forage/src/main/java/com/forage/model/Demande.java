package com.forage.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, unique = true)
    private String reference;

    @Column(length = 20)
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idCommune")
    private Commune commune;

    @OneToMany(mappedBy = "demande")
    private List<StatutDemande> statuts;

    public Demande(){}

    public Demande(String reference, String lieu, Client client, Commune commune){
        this.reference = reference;
        this.lieu = lieu;
        this.client = client;
        this.commune = commune;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Commune getCommune() { return commune; }
    public void setCommune(Commune commune) { this.commune = commune; }

    public List<StatutDemande> getStatuts() { return statuts; }
    public void setStatuts(List<StatutDemande> statuts) { this.statuts = statuts; }
}