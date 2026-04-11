package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}
