package com.forage.forage.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.forage.forage.model.StatutDemande;
import java.util.*;

public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Integer> {

    Optional<StatutDemande> findTopByDemandeIdOrderByDateStatutDesc(Integer idDemande);

    Optional<StatutDemande> findTopByDemande_IdOrderByDateStatutDesc(Integer idDemande);

    void deleteByDemande_Id(Integer idDemande);

    Optional<StatutDemande> findTopByDemande_IdOrderById(Integer idDemande) ;

    List<StatutDemande> findByDemandeIdOrderByIdAsc(Integer idDemande);
    
}
