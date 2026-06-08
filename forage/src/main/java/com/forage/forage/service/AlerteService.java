package com.forage.forage.service;

import com.forage.forage.model.*;
import com.forage.forage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AlerteService {

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private ParametresRepository parametresRepository;

    public List<String> verifierAlertesDemande(int idDemande) {
        List<String> alertesDeclenchees = new ArrayList<>();

        // 1. Récupération de l'historique dans l'ordre chronologique
        List<StatutDemande> historique = statutDemandeRepository.findByDemandeIdOrderByIdAsc(idDemande);
        
        // 2. Récupération de toutes les règles/seuils configurés
        List<Parametres> regles = parametresRepository.findAll();

        // S'il n'y a pas au moins 2 statuts, aucune transition n'a encore eu lieu
        if (historique.size() < 2) {
            return alertesDeclenchees;
        }

        // 3. On parcourt l'historique couple par couple
        for (int i = 1; i < historique.size(); i++) {
            StatutDemande statutPrecedent = historique.get(i - 1);
            StatutDemande statutActuel = historique.get(i);

            // On récupère l'objet Demande lié pour extraire ses infos
            Demande demande = statutActuel.getDemande();

            for (Parametres regle : regles) {
                
                // Est-ce que cette transition correspond à la règle (idStatut1 -> idStatut2) ?
                boolean matchStatut1 = statutPrecedent.getStatut().getId().equals(regle.getIdStatut1());
                boolean matchStatut2 = statutActuel.getStatut().getId().equals(regle.getIdStatut2());

                if (matchStatut1 && matchStatut2) {
                    
                    // Vérification du dépassement de temps (dt)
                    if (statutActuel.getDt() != null && regle.getDt() != null) {
                        if (statutActuel.getDt().compareTo(regle.getDt()) > 0) {
                            
                            // Construction d'une chaîne contenant toutes les métadonnées demandées
                            String codeAlerte = regle.getAlerte();
                            String refDemande = (demande != null) ? demande.getReference() : "N/A";
                            String lieuDemande = (demande != null) ? demande.getLieu() : "N/A";
                            
                            String sigleStatut1 = statutPrecedent.getStatut().getSigle();
                            String sigleStatut2 = statutActuel.getStatut().getSigle();
                            String libelleStatut2 = statutActuel.getStatut().getLibelle();

                            String message = String.format(
                                "Code: %s | Demande Ref: %s (Lieu: %s) | Transition: [%s -> %s] | " +
                                "Délai dépassé au statut '%s' : %s min constatées (Max autorisé: %s min)",
                                codeAlerte,
                                refDemande,
                                lieuDemande,
                                sigleStatut1,
                                sigleStatut2,
                                libelleStatut2,
                                statutActuel.getDt(),
                                regle.getDt()
                            );

                            alertesDeclenchees.add(message);
                        }
                    }
                }
            }
        }

        return alertesDeclenchees;
    }
}