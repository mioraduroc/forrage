package com.forage.forage.service;

import com.forage.forage.model.StatutDemande;
import com.forage.forage.repository.StatutDemandeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatutDemandeService {

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    public StatutDemande save(StatutDemande sd) {
        return statutDemandeRepository.save(sd);
    }

    @Transactional
    public void deleteAllByDemandeId(Integer idDemande) {
        statutDemandeRepository.deleteByDemande_Id(idDemande);
    }

    public StatutDemande deleteByDemandeId(Integer idDemande) {
        StatutDemande sd = statutDemandeRepository
                .findTopByDemande_IdOrderByDateStatutDesc(idDemande)
                .orElseThrow(() -> new RuntimeException("Aucun statut trouvé pour la demande id: " + idDemande));
        statutDemandeRepository.delete(sd);
        return sd;
    }
}
