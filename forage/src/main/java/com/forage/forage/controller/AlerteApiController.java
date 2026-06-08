package com.forage.forage.controller;

import com.forage.forage.service.AlerteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertes")
public class AlerteApiController {

    @Autowired
    private AlerteService alerteService;

    @GetMapping("/demande/{idDemande}")
    public ResponseEntity<List<String>> getAlertes(
            @PathVariable int idDemande,
            @RequestHeader(value = "X-Internal-Secret", required = true) String secret) {

                
        String cleAttendue = "SecretForage2026!";
        if (!cleAttendue.equals(secret)) {
            return ResponseEntity.status(403).build();
        }

        List<String> alertes = alerteService.verifierAlertesDemande(idDemande);
        return ResponseEntity.ok(alertes);
    }
}