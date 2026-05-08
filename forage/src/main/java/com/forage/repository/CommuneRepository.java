package com.forage.repository;

import com.forage.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuneRepository extends JpaRepository<Demande, Integer> {

} 
