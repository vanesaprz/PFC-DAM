package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Solicitud;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @GetMapping("/crear/{animalId}")
    public String mostrarFormulario(@PathVariable Long animalId, Model model) {
        Animal animal = animalRepository.findById(animalId).orElse(null);
        if (animal == null) return "redirect:/animales";

        model.addAttribute("animal", animal);
        model.addAttribute("solicitud", new Solicitud());
        return "formulario-adopcion";
    }

    @PostMapping("/guardar")
    public String guardarSolicitud(@ModelAttribute Solicitud solicitud, @RequestParam Long animalId, Principal principal) {
        solicitud.setAnimal(animalRepository.getReferenceById(animalId));
        String emailAdoptante = principal.getName();

        Cuenta cuenta = cuentaRepository.findByEmail(emailAdoptante).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow(() -> new RuntimeException("Adoptante no encontrada"));

        solicitud.setAdoptante(adoptante);

        solicitudRepository.save(solicitud);

        return "redirect:/animales/" + animalId + "?enviado=true";
    }
}


