package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Solicitud;
import com.example.PFC_DAM.model.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    //Devuelve todas las solicitudes de adopción que ha realizado un adoptante
    List<Solicitud> findByAdoptanteId(Long adoptanteId);

    //Devuelve true si un adoptante ya ha realizado una solicitud para un animal en concreto
    boolean existsByAdoptanteIdAndAnimalId(Long adoptanteId, Long animalId);

    //Lista las solicitudes para animales de la protectora
    List<Solicitud> findByAnimalProtectoraId(Long protectoraId);

    //Filtra las solicitudes de los animales de la protectora, no mostrando los del estado elegido (pensado para
    //que no se muestren en el panel de protectora las solicitudes rechazadas
    List<Solicitud> findByAnimalProtectoraIdAndEstadoNot(Long protectoraId, EstadoSolicitud estado);

    //Busca el número de solicitudes de adopción pendientes que tiene una protectora:
    long countByAnimalProtectoraIdAndEstado(Long protectoraId, EstadoSolicitud estado);

    List<Solicitud> findByAnimalId(Long animalId);
}

