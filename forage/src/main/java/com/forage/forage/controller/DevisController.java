package com.forage.forage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.forage.forage.model.*;
import com.forage.forage.service.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/devis")
public class DevisController {

    @Autowired
    private DevisService devisService;

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/formulaire")
    public String demande(Model model) {
        model.addAttribute("demandes", demandeService.listerDemandes());
        model.addAttribute("types", devisService.listerTypes());
        return "devisFormulaire";
    }

    @GetMapping("/demande/{id}")
    @ResponseBody
    public Map<String, Object> getDemande(@PathVariable("id") int id) {
        Demande demande = demandeService.getById(id);
        StatutDemande dernierStatut = demandeService.getDernierStatut(id);
        String sigleStatut = dernierStatut != null ? dernierStatut.getStatut().getSigle() : "Aucun";

        return Map.of(
                "id", demande.getId(),
                "reference", demande.getReference(),
                "client", demande.getClient() != null ? demande.getClient().getNom() : "",
                "lieu", demande.getLieu() != null ? demande.getLieu() : "",
                "commune", demande.getCommune() != null ? demande.getCommune().getLibelle() : "",
                "statutSigle", sigleStatut,
                "statutLibelle", dernierStatut != null ? dernierStatut.getStatut().getLibelle() : "Aucun",
                "typeId", "DEA".equals(sigleStatut) ? 2 : 1);
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("idDemande") int idDemande,
            @RequestParam("idType") int idType,
            @RequestParam(value = "observation", required = false) String observation,
            @RequestParam("libelle") List<String> libelles,
            @RequestParam("quantite") List<Integer> quantites,
            @RequestParam("prixUnitaire") List<BigDecimal> prixUnitaires) {

        devisService.creerDevis(idDemande, idType, observation, libelles, quantites, prixUnitaires);

        return "redirect:/demande/liste";
    }

    // @GetMapping("/delete/{id}")
    // public String delete(@PathVariable("id") int id) {

    //     System.out.println("ID A SUPPRIMER : " + id);

    //     demandeService.deleteById(id);

    //     return "redirect:/devis/liste";
    // }

    // @GetMapping("/edit/{id}")
    // public String edit(@PathVariable int id, Model model) {
    //     model.addAttribute("demande", demandeService.getById(id));
    //     model.addAttribute("clients", demandeService.listerClients());
    //     model.addAttribute("communes", demandeService.listerCommunes());
    //     return "demandeFormulaire";
    // }

    // @GetMapping("/liste")
    // public String liste(Model model) {

    //     List<Demande> demandes = demandeService.listerDemandes();

    //     Map<Integer, String> statuts = new HashMap<>();

    //     for (Demande d : demandes) {
    //         statuts.put(d.getId(), demandeService.getStatutActuel(d.getId()));
    //     }

    //     model.addAttribute("demandes", demandes);
    //     model.addAttribute("statuts", statuts);

    //     return "listeDevis";
    // }
}
