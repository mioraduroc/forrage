package com.forage.service;

import com.forage.model.Demande;
import com.forage.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Demande save(Demande demande) {
        return demandeRepository.save(demande);
    }

    public Demande findById(int id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        demandeRepository.deleteById(id);
    }
}