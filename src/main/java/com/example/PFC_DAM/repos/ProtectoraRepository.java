package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Protectora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtectoraRepository extends JpaRepository<Protectora, Long> {
}
