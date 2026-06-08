package com.forage.forage.service;

import com.forage.forage.model.*;
import com.forage.forage.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.time.Duration;


@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutDemandeService statutDemandeService;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    public String getStatutActuel(Demande d) {

        return d.getStatutDemandes().stream()
                .max(Comparator.comparing(StatutDemande::getDateStatut))
                .map(sd -> sd.getStatut().getSigle())
                .orElse("Aucun");
    }

    @Transactional    // grace a ca spring garantit que tout soit reussi ou tout soit annule
    public Demande deleteById(int id) {

        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable avec id: " + id));

        statutDemandeService.deleteAllByDemandeId(id);

        demandeRepository.delete(demande);

        return demande;
    }

    public Demande getById(int id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable avec id: " + id));
    }

    public String getStatutActuel(int idDemande) {

        return statutDemandeRepository
                .findTopByDemandeIdOrderByDateStatutDesc(idDemande)
                .map(sd -> sd.getStatut().getSigle())
                .orElse("Aucun");
    }

    public StatutDemande getDernierStatut(int idDemande) {
        return statutDemandeRepository
                .findTopByDemandeIdOrderByDateStatutDesc(idDemande)
                .orElse(null);
    }

    public List<Demande> listerDemandes() {
        return demandeRepository.findAllWithStatuts();
    }

    public Demande creerDemande(Demande demande, LocalDateTime dateCreation) {

        Demande saved = demandeRepository.save(demande);

        Statut statut = statutRepository.findBySigle("DC")
                .orElseThrow(() -> new RuntimeException("Statut DC introuvable"));

        // StatutDemande st = statutDemandeService.getLastStatutDemandeByDemande_Id(saved.getId()) ;

        // BigDecimal dt = BigDecimal.ZERO;

        // if (st != null){

        //     Duration duree = Duration.between(st.getDateStatut(),dateCreation);  
        //     Long minutes = duree.toMinutes();
        //     dt = BigDecimal.valueOf(minutes);
        // }
        
        // StatutDemande sd = new StatutDemande();
        // sd.setDemande(saved);
        // sd.setStatut(statut);
        // sd.setDateStatut(dateCreation);
        // sd.setDt(dt); // duree en minute entre le nouveau statut et le dernier statut de la demande 

        // statutDemandeService.save(sd);

        ajouterStatut(saved.getId(),statut.getId(),dateCreation) ;

        return saved;
    }

    public List<Client> listerClients() {
        return clientRepository.findAll();
    }

    public List<Commune> listerCommunes() {
        return communeRepository.findAll();
    }

    public List<Statut> listerStatuts() {
        return statutRepository.findAll();
    }

    public StatutDemande ajouterStatut(int idDemande, int idStatut, LocalDateTime dateStatut) {
        Demande demande = getById(idDemande);
        Statut statut = statutRepository.findById(idStatut)
                .orElseThrow(() -> new RuntimeException("Statut introuvable avec id: " + idStatut));

        StatutDemande st = getDernierStatut(idDemande) ;

        BigDecimal dt = BigDecimal.ZERO;

        if (st != null){

            long minutesOuvrees = calculerMinutesOuvrables(st.getDateStatut(), dateStatut);
            dt = BigDecimal.valueOf(minutesOuvrees);
        }

        StatutDemande sd = new StatutDemande();
        sd.setDemande(demande);
        sd.setStatut(statut);
        sd.setDateStatut(dateStatut);
        sd.setDt(dt);

        return statutDemandeService.save(sd);
    }

    private long calculerMinutesOuvrables(LocalDateTime debut, LocalDateTime fin) {
        if (debut == null || fin == null || debut.isAfter(fin)) {
            return 0;
        }

        long minutesComptees = 0;
        LocalDateTime curseur = debut;

        //  bornes de travail
        int heureOuverture = 8;
        int heureFermeture = 16;

        // Boucle minute par minute
        while (curseur.isBefore(fin)) {
            java.time.DayOfWeek jour = curseur.getDayOfWeek();
            int heure = curseur.getHour();

            if (jour != java.time.DayOfWeek.SATURDAY && jour != java.time.DayOfWeek.SUNDAY) {
                
                if (heure >= heureOuverture && heure < heureFermeture) {
                    minutesComptees++;
                }
            }

            // On passe à la minute suivante
            curseur = curseur.plusMinutes(1);
        }

        return minutesComptees;
    }
}
