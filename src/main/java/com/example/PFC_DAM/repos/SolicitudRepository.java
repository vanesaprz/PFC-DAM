package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByAdoptanteId(Long adoptanteId);
}

