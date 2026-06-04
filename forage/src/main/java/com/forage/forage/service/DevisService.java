package com.forage.forage.service;

import com.forage.forage.model.Demande;
import com.forage.forage.model.DetailDevis;
import com.forage.forage.model.Devis;
import com.forage.forage.model.Statut;
import com.forage.forage.model.StatutDemande;
import com.forage.forage.model.Type;
import com.forage.forage.repository.DemandeRepository;
import com.forage.forage.repository.DetailDevisRepository;
import com.forage.forage.repository.DevisRepository;
import com.forage.forage.repository.StatutRepository;
import com.forage.forage.repository.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private DetailDevisRepository detailDevisRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutDemandeService statutDemandeService;

    public List<Type> listerTypes() {
        return typeRepository.findAll();
    }

    @Transactional
    public Devis creerDevis(int idDemande, int idType, String observation,
            List<String> libelles, List<Integer> quantites, List<BigDecimal> prixUnitaires) {

        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable avec id: " + idDemande));
        Type type = typeRepository.findById(idType)
                .orElseThrow(() -> new RuntimeException("Type de devis introuvable avec id: " + idType));

        Devis devis = new Devis();
        devis.setDemande(demande);
        devis.setType(type);
        devis.setCreatedAt(LocalDateTime.now());
        devis.setObservation(observation);
        Devis saved = devisRepository.save(devis);

        int detailCount = Math.min(libelles.size(), Math.min(quantites.size(), prixUnitaires.size()));
        for (int i = 0; i < detailCount; i++) {
            String libelle = libelles.get(i);
            if (libelle == null || libelle.isBlank()) {
                continue;
            }

            DetailDevis detail = new DetailDevis();
            detail.setDevis(saved);
            detail.setLibelle(libelle);
            detail.setQuantite(quantites.get(i) != null ? quantites.get(i) : 0);
            detail.setPrixUnitaire(prixUnitaires.get(i) != null ? prixUnitaires.get(i) : BigDecimal.ZERO);
            detailDevisRepository.save(detail);
        }

        ajouterStatutDevisCree(demande, idType);

        return saved;
    }

    private void ajouterStatutDevisCree(Demande demande, int idType) {
        String sigle = idType == 2 ? "DFC" : "DEC";
        Statut statut = statutRepository.findBySigle(sigle)
                .orElseThrow(() -> new RuntimeException("Statut " + sigle + " introuvable"));

        StatutDemande sd = new StatutDemande();
        sd.setDemande(demande);
        sd.setStatut(statut);
        sd.setDateStatut(LocalDateTime.now());

        statutDemandeService.save(sd);
    }
}
