package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Protectora;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/protectora")
@PreAuthorize("hasAnyRole('PROTECTORA', 'ADMIN')")
public class ProtectoraPanelController {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/panel")
    public String mostrarPanel(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElse(null);
        Protectora protectora = cuenta.getProtectora();

        //Para las tarjetas de administración:
        model.addAttribute("cantEnAdopcion", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.DISPONIBLE));
        model.addAttribute("cantAdoptados", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.ADOPTADO));
        model.addAttribute("cantUrgentes", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.URGENTE));
        //Tengo pendiente contabilizar las solicitudes por ahora


        //Para tabla animales:
        List<Animal> misAnimales = animalRepository.findByProtectoraId(protectora.getId());
        model.addAttribute("animales", misAnimales);


        //Destacar el menu activo en la vista:
        model.addAttribute("menuActivo", "animales");

        return "protectora/panel-animales";
    }

    @GetMapping("/animales/crear")
    public String formularioNuevoAnimal(Model model) {
        return "protectora/formulario-animal";
    }

    @GetMapping("/solicitudes")
    public String verSolicitudesRecibidas(Model model) {
        return ("protectora/solicitudes-recibidas");
    }


}
