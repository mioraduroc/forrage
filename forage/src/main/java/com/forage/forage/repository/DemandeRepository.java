package com.forage.forage.repository;

import com.forage.forage.model.Demande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List ;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {

    @Query("""
        SELECT DISTINCT d FROM Demande d
        LEFT JOIN FETCH d.statutDemandes
    """)
    List<Demande> findAllWithStatuts();


}
