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
        List<StatutDemande> historique = statutDemandeRepository.findByDemandeIdOrderByIdAsc(idDemande);
        List<Parametres> regles = parametresRepository.findAll();

        if (historique.size() < 2) {
            return alertesDeclenchees;
        }

        for (int i = 1; i < historique.size(); i++) {
            StatutDemande statutPrecedent = historique.get(i - 1);
            StatutDemande statutActuel = historique.get(i);

            Demande demande = statutActuel.getDemande();

            for (Parametres regle : regles) {
                
                boolean matchStatut1 = statutPrecedent.getStatut().getId().equals(regle.getIdStatut1());
                boolean matchStatut2 = statutActuel.getStatut().getId().equals(regle.getIdStatut2());

                if (matchStatut1 && matchStatut2) {
                    if (statutActuel.getDt() != null && regle.getDt() != null) {
                        if (statutActuel.getDt().compareTo(regle.getDt()) > 0) {
                            
                            String codeAlerte = regle.getAlerte();
                            String refDemande = (demande != null) ? demande.getReference() : "N/A";
                            String lieuDemande = (demande != null) ? demande.getLieu() : "N/A";
                            String clientDemande = (demande != null && demande.getClient() != null) ? demande.getClient().getNom() : "N/A";
                            String communeDemande = (demande != null && demande.getCommune() != null) ? demande.getCommune().getLibelle() : "N/A";
                            String districtDemande = (demande != null && demande.getCommune() != null && demande.getCommune().getDistrict() != null) ? demande.getCommune().getDistrict().getLibelle() : "N/A";
                            String regionDemande = (demande != null && demande.getCommune() != null && demande.getCommune().getDistrict() != null && demande.getCommune().getDistrict().getRegion() != null) ? demande.getCommune().getDistrict().getRegion().getLibelle() : "N/A";

                            String sigleStatut1 = statutPrecedent.getStatut().getSigle();
                            String sigleStatut2 = statutActuel.getStatut().getSigle();
                            String libelleStatut2 = statutActuel.getStatut().getLibelle();

                            // Extraction des dates des statuts (Ajuste .getDateStatut() si ton champ s'appelle autrement, ex: .getCreated())
                            String datePrecedent = (statutPrecedent.getDateStatut() != null) ? statutPrecedent.getDateStatut().toString() : "N/A";
                            String dateActuel = (statutActuel.getDateStatut() != null) ? statutActuel.getDateStatut().toString() : "N/A";

                            // On injecte les dates dans la chaîne textuelle [Date: ...]
                            String message = String.format(
                                "Code: %s | Demande Ref: %s (Client: %s Lieu: %s Commune: %s District: %s Region: %s) | Dates: [%s -> %s] | Transition: [%s -> %s] | " +
                                "Délai dépassé au statut '%s' : %s min constatées (Max autorisé: %s min)",
                                codeAlerte,       
                                refDemande,      
                                clientDemande,   
                                lieuDemande,      
                                communeDemande,  
                                districtDemande, 
                                regionDemande,
                                datePrecedent,  
                                dateActuel,  
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