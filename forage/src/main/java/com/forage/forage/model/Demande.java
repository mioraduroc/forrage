package com.forage.forage.model;

import jakarta.persistence.*;
import java.util.List;  


@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @Column(length = 20)
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "idCommune")
    private Commune commune;

    @OneToMany(mappedBy = "demande", fetch = FetchType.LAZY)
    private List<StatutDemande> statutDemandes;


    public Demande() {
    }

    public Demande(String reference, Client client, String lieu, Commune commune) {
        this.reference = reference;
        this.client = client;
        this.lieu = lieu;
        this.commune = commune;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public List<StatutDemande> getStatutDemandes() {
        return statutDemandes;
    }

    public void setStatutDemandes(List<StatutDemande> statutDemandes) {
        this.statutDemandes = statutDemandes;
    }


    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", client=" + client +
                ", commune=" + commune +
                ", lieu='" + lieu + '\'' +
                '}';
    }

}
