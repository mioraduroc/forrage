package com.forage.forage.repository;

import com.forage.forage.model.DetailDevis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailDevisRepository extends JpaRepository<DetailDevis, Integer> {
}
