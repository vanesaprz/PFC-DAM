package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    //Devuelve todas las solicitudes de adopción que ha realizado un adoptante
    List<Solicitud> findByAdoptanteId(Long adoptanteId);

    //Devuelve true si un adoptante ya ha realizado una solicitud para un animal en concreto
    boolean existsByAdoptanteIdAndAnimalId(Long adoptanteId, Long animalId);


}

