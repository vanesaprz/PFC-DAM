package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    //Por especie:
    List<Animal> findByEspecie(String especie);

    List<Animal> findByEstado(EstadoAnimal estado);

    //Pensado para incluir los recién llegados en la página principal
    List<Animal> findTop10ByEstadoOrderByFechaIngresoDesc(EstadoAnimal estado);

    //PARA FILTRO DE BÚSQUEDA
    @Query("SELECT a FROM Animal a WHERE " +
            "(:especie IS NULL OR a.especie = :especie) AND " +
            "(:provincia IS NULL OR a.protectora.provincia = :provincia) AND " +
            "(:tamano IS NULL OR a.tamano = :tamano) AND " +
            "(cast(:fechaInicio as date) IS NULL OR a.fechaNacimiento>= :fechaInicio) AND " +
            "(cast(:fechaFin as date) IS NULL OR a.fechaNacimiento <= :fechaFin) AND " +
            "(a.estado = 'DISPONIBLE' OR a.estado = 'URGENTE')")
    List<Animal> buscarConFiltros(
            @Param("especie") String especie,
            @Param("provincia") String provincia,
            @Param("tamano") String tamano,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

}
