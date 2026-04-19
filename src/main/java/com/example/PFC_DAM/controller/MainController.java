package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.repos.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Devuelve los animales marcados como URGENTES
        model.addAttribute("urgentes", animalRepository.findByEstado(EstadoAnimal.URGENTE));

        //Devuelve los 10 animales que han llegado más recientemente... se quitan los urgentes porque ya están arriba
        model.addAttribute("recientes", animalRepository.findTop10ByEstadoOrderByFechaIngresoDesc(EstadoAnimal.DISPONIBLE));

        return "index";
    }

    @GetMapping("/registro/seleccion")
    public String mostrarSeleccionRegistro() {
        return "registro-seleccion";
    }

}
