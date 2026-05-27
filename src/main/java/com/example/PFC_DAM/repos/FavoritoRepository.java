package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    //Lista todos los favoritos de un adoptante
    List<Favorito> findByAdoptanteId(Long adoptanteId);

    //Indica si el adoptante ya había marcado un animal como favorito
    boolean existsByAdoptanteIdAndAnimalId(Long adoptanteId, Long animalId);

    //Si existe una coincidencia, devuelve el favorito. Si no, devuelve un objeto vacío
    Optional<Favorito> findByAdoptanteIdAndAnimalId(Long adoptanteId, Long animalId);

    List<Favorito> findByAnimalId(Long animalId);

    
}
