package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.*;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ProtectoraRepository protectoraRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Devuelve los animales marcados como URGENTES
        model.addAttribute("urgentes", animalRepository.findByEstado(EstadoAnimal.URGENTE));

        //Devuelve los 10 animales que han llegado más recientemente... se quitan los urgentes porque ya están arriba
        model.addAttribute("recientes", animalRepository.findTop8ByEstadoOrderByFechaIngresoDesc(EstadoAnimal.DISPONIBLE));

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


    //Consulta de solicitudes realizadas:
    @GetMapping("/adoptante/solicitudes")
    public String verMisSolicitudes(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElse(null);
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        List<Solicitud> solicitudes = solicitudRepository.findByAdoptanteId(adoptante.getId());
        model.addAttribute("solicitudes", solicitudes);

        return "adoptante/mis-solicitudes";
    }

    @GetMapping("/protectora/{id}")
    public String verDetalleProtectora(@PathVariable Long id, Model model) {
        Protectora protectora = protectoraRepository.findById(id).orElseThrow();
        model.addAttribute("protectora", protectora);

        List<Animal> animalesOrdenados = protectora.getAnimales().stream()
                .filter(a -> a.getEstado() == EstadoAnimal.URGENTE || a.getEstado() == EstadoAnimal.DISPONIBLE)
                .sorted(Comparator.comparing(a -> a.getEstado() == EstadoAnimal.URGENTE ? 0 : 1))
                .collect(Collectors.toList());

        model.addAttribute("animalesProtectora", animalesOrdenados);

        return "detalle-protectora";
    }


}
