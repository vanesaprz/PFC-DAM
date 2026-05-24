package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Solicitud;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.EstadoSolicitud;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Transactional
    public void actualizarEstado(Long solicitudId, EstadoSolicitud nuevoEstado, String notas) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow();
        solicitud.setEstado(nuevoEstado);
        solicitud.setNotas(notas);
        solicitudRepository.save(solicitud);

        notificacionService.crear(
                solicitud.getAdoptante().getCuenta(), "Tu solicitud para adoptar a " +
                        solicitud.getAnimal().getNombre() + " ha cambiado de estado a: " + nuevoEstado
        );


        // Si la solicitud se aprueba, el animal pasa a estado ADOPTADO
        if (nuevoEstado == EstadoSolicitud.APROBADA) {
            solicitud.getAnimal().setEstado(EstadoAnimal.ADOPTADO);
            animalRepository.save(solicitud.getAnimal());
        }
    }
}