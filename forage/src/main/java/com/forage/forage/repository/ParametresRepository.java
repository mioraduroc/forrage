package com.forage.forage.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.forage.forage.model.Parametres;

@Repository
public interface ParametresRepository extends JpaRepository<Parametres, Integer> {
    

    Optional<Parametres> findByIdStatut1AndIdStatut2(Integer idStatut1, Integer idStatut2);
}