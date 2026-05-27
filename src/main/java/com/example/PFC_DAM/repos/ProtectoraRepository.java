package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Protectora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtectoraRepository extends JpaRepository<Protectora, Long> {
    Optional<Protectora> findByCuenta(Cuenta cuenta);

}
