package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long> {

    Optional<Adoptante> findByCuenta(Cuenta cuenta);
}
