package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.DTO.AnimalDTO;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.EstadoSolicitud;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.FavoritoRepository;
import com.example.PFC_DAM.repos.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public Animal guardarAnimal(AnimalDTO dto, MultipartFile foto, Cuenta cuenta) throws IOException {

        Animal animal;
        if (dto.getId() != null) {
            animal = animalRepository.findById(dto.getId()).orElseThrow();
        } else {
            animal = new Animal();
        }

        animal.setNombre(dto.getNombre());
        animal.setEspecie(dto.getEspecie());
        animal.setRaza(dto.getRaza());
        animal.setSexo(dto.getSexo());
        animal.setFechaNacimiento(dto.getFechaNacimiento());
        animal.setFechaIngreso(dto.getFechaIngreso());
        animal.setTamano(dto.getTamano());
        animal.setPeso(dto.getPeso());
        animal.setEsterilizado(dto.getEsterilizado() != null ? dto.getEsterilizado() : false);
        animal.setVacunado(dto.getVacunado() != null ? dto.getVacunado() : false);
        animal.setDesparasitado(dto.getDesparasitado() != null ? dto.getDesparasitado() : false);
        animal.setMicrochip(dto.getMicrochip() != null ? dto.getMicrochip() : false);
        animal.setAptoPerros(dto.getAptoPerros() != null ? dto.getAptoPerros() : false);
        animal.setAptoGatos(dto.getAptoGatos() != null ? dto.getAptoGatos() : false);
        animal.setAptoNinos(dto.getAptoNinos() != null ? dto.getAptoNinos() : false);
        animal.setNecesidadesEspeciales(dto.getNecesidadesEspeciales());
        animal.setNivelActividad(dto.getNivelActividad());
        animal.setMiedos(dto.getMiedos());
        animal.setDescripcion(dto.getDescripcion());
        animal.setProtectora(cuenta.getProtectora());

        // Gestión de la foto
        if (foto != null && !foto.isEmpty()) {
            String urlFoto = cloudinaryService.subirImagen(foto);
            animal.setFotoPrincipal(urlFoto);
        } else if (dto.getId() != null) {
            Animal animalExistente = animalRepository.findById(dto.getId()).orElseThrow();
            animal.setFotoPrincipal(animalExistente.getFotoPrincipal());
        }

        // Si el animal pasa a ADOPTADO, cerramos solicitudes pendientes y eliminamos favoritos
        if (dto.getEstado() == EstadoAnimal.ADOPTADO) {
            solicitudRepository.findByAnimalId(animal.getId()).stream()
                    .filter(s -> s.getEstado() != EstadoSolicitud.APROBADA)
                    .forEach(s -> s.setEstado(EstadoSolicitud.RECHAZADA));
            solicitudRepository.saveAll(solicitudRepository.findByAnimalId(animal.getId()));
            favoritoRepository.deleteAll(favoritoRepository.findByAnimalId(animal.getId()));
        }

        animal.setEstado(dto.getEstado());

        return animalRepository.save(animal);
    }

    @Transactional
    public void borrarAnimal(Long id, Cuenta cuenta) {
        Animal animal = animalRepository.findById(id).orElseThrow();

        if (!animal.getProtectora().getId().equals(cuenta.getProtectora().getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este animal");
        }

        solicitudRepository.deleteAll(solicitudRepository.findByAnimalId(id));
        favoritoRepository.deleteAll(favoritoRepository.findByAnimalId(id));
        animalRepository.delete(animal);
    }
}