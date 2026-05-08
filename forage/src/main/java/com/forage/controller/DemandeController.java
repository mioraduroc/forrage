package com.forage.controller;

import com.forage.model.Demande;
import com.forage.repository.ClientRepository;
import com.forage.repository.CommuneRepository;
import com.forage.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommuneRepository communeRepository;

    // 🔹 afficher formulaire
    @GetMapping("/form")
    public String showForm(Model model) {

        model.addAttribute("demande", new Demande());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("communes", communeRepository.findAll());

        return "demande-form";
    }

    // 🔹 sauvegarder
    @PostMapping("/save")
    public String save(@ModelAttribute Demande demande) {

        demandeService.save(demande);

        return "redirect:/demande/form";
    }

    // 🔹 liste demandes (optionnel mais utile)
    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("demandes", demandeService.findAll());

        return "demande-list";
    }
}