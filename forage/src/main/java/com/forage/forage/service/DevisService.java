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
import java.time.Duration;
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
    public Devis creerDevis( LocalDateTime dateCreation ,int idDemande, int idType, String observation,
            List<String> libelles, List<Integer> quantites, List<BigDecimal> prixUnitaires) {

        System.out.println("DevisService creerDevis ") ;
        System.out.println("dateCreation : "+dateCreation);
        System.out.println("idDemande : "+idDemande);
        System.out.println("idType : "+idType);
        System.out.println("observation : "+observation);
        
        for (String lbl : libelles) {
            System.out.println("libelles : "+lbl);
        }
        for (Integer qtt : quantites) {
            System.out.println("quatite : "+qtt);
        }
        for (BigDecimal pu : prixUnitaires) {
            System.out.println("prix unitaire : "+pu);
        }

        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable avec id: " + idDemande));
        Type type = typeRepository.findById(idType)
                .orElseThrow(() -> new RuntimeException("Type de devis introuvable avec id: " + idType));
            
        
        Devis devis = new Devis();
        devis.setDemande(demande);
        devis.setType(type);
        devis.setCreatedAt(dateCreation);    // changer en date a inserer dans le formulaire
        devis.setObservation(observation);
        Devis saved = devisRepository.save(devis);

        int detailCount = Math.min(libelles.size(), Math.min(quantites.size(), prixUnitaires.size()));
        System.out.println("nombre detail devis "+detailCount);

        for (int i = 0; i < detailCount; i++) {
            
            String libelle = libelles.get(i);
            System.out.println("Devis : " + saved
                + "- libelle : " + libelle 
                + "- quantite : " + (quantites.get(i) != null ? quantites.get(i) : 0) 
                + "- prix unitaire : " + (prixUnitaires.get(i) != null ? prixUnitaires.get(i) : BigDecimal.ZERO));

            if (libelle == null || libelle.isBlank()) {
                continue;
            }

            DetailDevis detail = new DetailDevis();

            detail.setDevis(saved);
            detail.setLibelle(libelle);
            detail.setQuantite(quantites.get(i) != null ? quantites.get(i) : 0);
            detail.setPrixUnitaire(prixUnitaires.get(i) != null ? prixUnitaires.get(i) : BigDecimal.ZERO);
            detailDevisRepository.save(detail);

            System.out.println("detail numero : "+(i+1)) ;
        }

        ajouterStatutDevisCree(dateCreation,demande, idType);

        return saved;
    }

    private void ajouterStatutDevisCree(LocalDateTime dateCreation ,Demande demande, int idType) {

        System.out.println("            DEBUUUUUUUUUUUUUUUUUG DevisService ajouterStatutDevisCree  ") ;
        String sigle = idType == 2 ? "DFC" : "DEC";
        System.out.println("            DEBUUUUUUUUUUUUUUUUUG sigle " + sigle);
        Statut statut = statutRepository.findBySigle(sigle)
                .orElseThrow(() -> new RuntimeException("Statut " + sigle + " introuvable"));

        System.out.println("            DEBUUUUUUUUUUUUUUUUUG statut  "+ statut.getId()+" "+ statut.getLibelle() ) ;
 
        StatutDemande st = statutDemandeService.getLastStatutDemandeByDemande_Id(demande.getId()) ;
        if(st == null){
            st = statutDemandeService.getByIdDemande(demande.getId());
        }

        System.out.println("            DEBUUUUUUUUUUUUUUUUUG st  "+ st.getDt() ) ; // ato 

        BigDecimal dt = BigDecimal.ZERO;

        if (st != null){

            Duration duree = Duration.between(st.getDateStatut(),dateCreation);  
            Long minutes = duree.toMinutes();
            dt = BigDecimal.valueOf(minutes);
        }

        StatutDemande sd = new StatutDemande();
        sd.setDemande(demande);
        sd.setStatut(statut);
        sd.setDateStatut(dateCreation);
        sd.setDt(dt);

        statutDemandeService.save(sd);
    }
}
