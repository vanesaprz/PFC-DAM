package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Adoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long> {
}
