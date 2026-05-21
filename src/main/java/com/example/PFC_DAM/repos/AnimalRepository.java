package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.enums.Especie;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.Tamano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    //Por especie:
    List<Animal> findByEspecie(Especie especie);

    //Para los urgentes de la página principal:
    List<Animal> findByEstado(EstadoAnimal estado);

    //Pensado para incluir los recién llegados en la página principal:
    List<Animal> findTop8ByEstadoOrderByFechaIngresoDesc(EstadoAnimal estado);

    //Para el panel privado de la protectora:
    List<Animal> findByProtectoraId(Long protectoraId);

    long countByProtectoraIdAndEstado(Long protectoraId, EstadoAnimal estado);


    //PARA FILTRO DE BÚSQUEDA
    @Query("SELECT a FROM Animal a WHERE " +
            "(:especie IS NULL OR a.especie = :especie) AND " +
            "(:provincia IS NULL OR a.protectora.provincia = :provincia) AND " +
            "(:tamano IS NULL OR a.tamano = :tamano) AND " +
            "(cast(:fechaInicio as date) IS NULL OR a.fechaNacimiento>= :fechaInicio) AND " +
            "(cast(:fechaFin as date) IS NULL OR a.fechaNacimiento <= :fechaFin) AND " +
            "(a.estado = 'DISPONIBLE' OR a.estado = 'URGENTE')")
    List<Animal> buscarConFiltros(
            @Param("especie") Especie especie,
            @Param("provincia") String provincia,
            @Param("tamano") Tamano tamano,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

}
