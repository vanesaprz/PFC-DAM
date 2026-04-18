package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByEmail(String email);
}
