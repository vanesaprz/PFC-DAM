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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String guardarSolicitud(@ModelAttribute Solicitud solicitud, @RequestParam Long animalId, Principal principal, RedirectAttributes redirectAttributes) {
        solicitud.setAnimal(animalRepository.getReferenceById(animalId));
        String emailAdoptante = principal.getName();

        Cuenta cuenta = cuentaRepository.findByEmail(emailAdoptante).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow(() -> new RuntimeException("Adoptante no encontrada"));

        if (solicitudRepository.existsByAdoptanteIdAndAnimalId(adoptante.getId(), animalId)) {
            redirectAttributes.addFlashAttribute("error", "Ya has enviado una solicitud para este animal");
            return "redirect:/animales/" + animalId;
        }
        solicitud.setAdoptante(adoptante);
        solicitud.setAnimal(animalRepository.getReferenceById(animalId));
        solicitudRepository.save(solicitud);

        return "redirect:/animales/" + animalId + "?enviado=true";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarSolicitud(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow();
        //Verifico que la solicitud la ha realizado la misma cuenta que está pidiendo que se elimine
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        if (!solicitud.getAdoptante().getId().equals(adoptante.getId())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para borrar esta solicitud");
            return "redirect:/adoptante/solicitudes";
        }

        solicitudRepository.delete(solicitud);
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud eliminada correctamente");
        return "redirect:/adoptante/solicitudes";
    }
}


