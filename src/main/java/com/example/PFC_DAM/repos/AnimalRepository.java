package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    //Por especie:
    List<Animal> findByEspecie(String especie);

    List<Animal> findByEstado(EstadoAnimal estado);

    //Pensado para incluir los recién llegados en la página principal
    List<Animal> findTop10ByEstadoOrderByFechaIngresoDesc(EstadoAnimal estado);
}
