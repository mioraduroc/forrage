package com.forage.forage.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parametres")
public class Parametres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idStatut1")
    private Integer idStatut1;

    @Column(name = "idStatut2")
    private Integer idStatut2;

    @Column(name = "dt", precision = 10, scale = 2)
    private BigDecimal dt;

    @Column(name = "alerte")
    private String alerte;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdStatut1() {
        return idStatut1;
    }

    public void setIdStatut1(Integer idStatut1) {
        this.idStatut1 = idStatut1;
    }

    public Integer getIdStatut2() {
        return idStatut2;
    }

    public void setIdStatut2(Integer idStatut2) {
        this.idStatut2 = idStatut2;
    }

    public BigDecimal getDt() {
        return dt;
    }

    public void setDt(BigDecimal dt) {
        this.dt = dt;
    }

    public String getAlerte() {
        return alerte;
    }

    public void setAlerte(String alerte) {
        this.alerte = alerte;
    }
}