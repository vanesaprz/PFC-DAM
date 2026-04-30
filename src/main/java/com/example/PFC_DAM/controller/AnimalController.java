package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.repos.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/animales")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("")
    public String listarAnimales(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String tamano,
            @RequestParam(required = false) String edad,
            Model model) {

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        LocalDate hoy = LocalDate.now();
        //Calculo los rangos de fechas correspondientes a Cachorro, Joven, etc..

        if (edad != null && !edad.isEmpty()) {
            switch (edad) {
                case "Cachorro":
                    //de 0 a 1 año
                    fechaInicio = hoy.minusYears(1);
                    fechaFin = hoy;
                    break;

                case "Joven":
                    //De 1 a 3 años
                    fechaInicio = hoy.minusYears(3);
                    fechaFin = hoy.minusYears(1).minusDays(1);
                    break;
                case "Adulto":
                    //De 3 a 7 años
                    fechaInicio = hoy.minusYears(7);
                    fechaFin = hoy.minusYears(3).minusDays(1);
                    break;

                case "Senior":
                    fechaInicio = hoy.minusYears(100);
                    fechaFin = hoy.minusYears(7).minusDays(1);
                    break;
            }
        }
        String e = (especie != null && !especie.isEmpty()) ? especie : null;
        String p = (provincia != null && !provincia.isEmpty()) ? provincia : null;
        String t = (tamano != null && !tamano.isEmpty()) ? tamano : null;

        List<Animal> animales = animalRepository.buscarConFiltros(e, p, t, fechaInicio, fechaFin);
        model.addAttribute("animales", animales);
        return "animales";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Animal animal = animalRepository.findById(id).orElse(null);

        if (animal == null) {
            return "redirect:/animales/";
        }

        model.addAttribute("animal", animal);


        //PENDIENTE INCLUIR LISTA DE TODAS LAS FOTOS PARA GALERÍA/CARRUSEL

        return "detalles";
    }

    @GetMapping("/{id}/adoptar")
    public String formularioAdopcion(@PathVariable Long id, Model model) {
        return "formulario-adopcion";
    }
}
