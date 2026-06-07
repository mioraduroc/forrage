package com.forage.forage.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.forage.forage.model.StatutDemande;

public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Integer> {

    Optional<StatutDemande> findTopByDemandeIdOrderByDateStatutDesc(Integer idDemande);

    Optional<StatutDemande> findTopByDemande_IdOrderByDateStatutDesc(Integer idDemande);

    void deleteByDemande_Id(Integer idDemande);

    Optional <StatutDemande> findLastByStatut_IdOrderById(Integer idStatut) ;

    StatutDemande findTopByDemande_IdOrderById(Integer idDemande) ;
}
