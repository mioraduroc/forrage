package com.forage.forage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.forage.forage.model.Demande;
import com.forage.forage.service.DemandeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/formulaire")
    public String demande(Model model) {
        model.addAttribute("demande", new Demande());
        model.addAttribute("clients", demandeService.listerClients());
        model.addAttribute("communes", demandeService.listerCommunes());
        return "demandeFormulaire";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Demande demande) {

        // System.out.println(" AVANT SERVICE ");
        // System.out.println(demande);

        demandeService.creerDemande(demande);
    
        return "redirect:/demande/formulaire";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {

        System.out.println("ID A SUPPRIMER : " + id);

        demandeService.deleteById(id);

        return "redirect:/demande/liste";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("demande", demandeService.getById(id));
        model.addAttribute("clients", demandeService.listerClients());
        model.addAttribute("communes", demandeService.listerCommunes());
        return "demandeFormulaire";
    }

    // @GetMapping("/edit/{id}")
    // public String edit(@PathVariable int id, Model model) {
    //     model.addAttribute("demande", demandeService.getById(id));
    //     model.addAttribute("clients", demandeService.listerClients());
    //     model.addAttribute("communes", demandeService.listerCommunes());
    //     return "demandeFormulaire";
    // }

    @GetMapping("/liste")
    public String liste(Model model) {

        List<Demande> demandes = demandeService.listerDemandes();

        Map<Integer, String> statuts = new HashMap<>();

        for (Demande d : demandes) {
            statuts.put(d.getId(), demandeService.getStatutActuel(d.getId()));
        }

        model.addAttribute("demandes", demandes);
        model.addAttribute("statuts", statuts);

        return "listeDemande";
    }

    @GetMapping("/statut/formulaire")
    public String statutFormulaire(Model model) {
        model.addAttribute("demandes", demandeService.listerDemandes());
        model.addAttribute("statutsDisponibles", demandeService.listerStatuts());
        return "statutModificationFormulaire";
    }


    @PostMapping("/statut/save")
    public String saveStatut(
            @RequestParam("idDemande") int idDemande,   // ampiana nom an'ilay parametre 
            @RequestParam("idStatut") int idStatut,
            @RequestParam("dateStatut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStatut) {

        demandeService.ajouterStatut(idDemande, idStatut, dateStatut);

        return "redirect:/demande/liste";
    }

    // @PostMapping("/statut/save")
    // public String saveStatut(@RequestParam int idDemande,
    //         @RequestParam int idStatut,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStatut) {

    //     demandeService.ajouterStatut(idDemande, idStatut, dateStatut);

    //     return "redirect:/demande/liste";
    // }
}
