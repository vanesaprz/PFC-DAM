package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Protectora;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.ProtectoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ProtectoraRepository protectoraRepository;

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

    //Protectoras en navbar:
    @GetMapping("/protectoras")
    public String listarProtectoras(Model model) {
        model.addAttribute("protectoras", protectoraRepository.findAll());
        return "listado-protectoras";
    }

    //Pantalla Mi Perfil
    @GetMapping("/perfil")
    public String verPerfil() {
        return "adoptante/perfil";
    }

    //Pantalla favoritos
    @GetMapping("/adoptante/favoritos")
    public String verFavoritos() {
        return "adoptante/favoritos";
    }

    //Consulta de solicitudes realizadas:
    @GetMapping("/adoptante/solicitudes")
    public String verMisSolicitudes(Model model, Principal principal) {
        return "adoptante/mis-solicitudes";

    }

    @GetMapping("/protectora/{id}")
    public String verDetalleProtectora(@PathVariable Long id, Model model) {
        Protectora protectora = protectoraRepository.findById(id).orElseThrow();
        model.addAttribute("protectora", protectora);
        return "detalle-protectora";
    }


}
