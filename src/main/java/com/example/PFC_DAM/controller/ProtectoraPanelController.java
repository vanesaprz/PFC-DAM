package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Protectora;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.Sexo;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //Formulario de subida de animales
    @GetMapping("/animales/nuevo")
    public String formularioNuevoAnimal(Model model) {
        Animal nuevoAnimal = new Animal();
        //Valores por defecto:

        nuevoAnimal.setVacunado(false);
        nuevoAnimal.setEsterilizado(false);
        nuevoAnimal.setDesparasitado(false);
        nuevoAnimal.setMicrochip(false);
        nuevoAnimal.setAptoPerros(false);
        nuevoAnimal.setAptoGatos(false);
        nuevoAnimal.setAptoNinos(false);
        nuevoAnimal.setEstado(EstadoAnimal.DISPONIBLE);

        model.addAttribute("animal", nuevoAnimal);

        model.addAttribute("especies", List.of("Perro", "Gato", "Otros"));
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("estados", EstadoAnimal.values());

        return "protectora/formulario-animal";
    }

    //Guardar formulario
    @PostMapping("/animales/guardar")
    public String guardarAnimal(@Valid @ModelAttribute("animal") Animal animal,
                                BindingResult result,
                                Principal principal,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Volvemos a cargar las listas necesarias para el formulario
            model.addAttribute("especies", List.of("Perro", "Gato", "Otros"));
            model.addAttribute("sexos", Sexo.values());
            model.addAttribute("estados", EstadoAnimal.values());
            return "protectora/formulario-animal"; // Se queda en la misma página mostrando errores
        }
        try {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

            animal.setProtectora(cuenta.getProtectora());

            // Si los checkbox no se marcan, llegan como null en lugar de false
            if (animal.getVacunado() == null) animal.setVacunado(false);
            if (animal.getEsterilizado() == null) animal.setEsterilizado(false);
            if (animal.getDesparasitado() == null) animal.setDesparasitado(false);
            if (animal.getMicrochip() == null) animal.setMicrochip(false);
            if (animal.getAptoPerros() == null) animal.setAptoPerros(false);
            if (animal.getAptoGatos() == null) animal.setAptoGatos(false);
            if (animal.getAptoNinos() == null) animal.setAptoNinos(false);

            // 3. Estado inicial (Tu entidad dice que no puede ser null)
            if (animal.getEstado() == null) {
                animal.setEstado(EstadoAnimal.DISPONIBLE);
            }

            animalRepository.save(animal);
            redirectAttributes.addFlashAttribute("mensaje", "¡Animal guardado con éxito!");
            return "redirect:/protectora/panel";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/protectora/animales/nuevo";
        }
    }

    @GetMapping("/solicitudes")
    public String verSolicitudesRecibidas(Model model) {
        return ("protectora/solicitudes-recibidas");
    }

    //Se crea el controlador para la eliminación de animales del panel de la protectora.
    @PostMapping("/animales/borrar/{id}")
    public String borrarAnimal(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Animal animal = animalRepository.findById(id).orElseThrow();

        //Verifico que el animal pertenece a la protectora que trata de borrar el animal:
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        if (!animal.getProtectora().getId().equals(cuenta.getProtectora().getId())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este animal");
            return "redirect:/protectora/panel";
        }
        animalRepository.delete(animal);
        redirectAttributes.addFlashAttribute("mensaje", "Animal eliminado correctamente");
        return "redirect:/protectora/panel";
    }

    //Controlador para editar los datos de los animales desde el panel de la protectora:

    @GetMapping("/animales/editar/{id}")
    public String formularioEditarAnimal(@PathVariable Long id, Model model, Principal principa, RedirectAttributes redirectAttributes) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        Cuenta cuenta = cuentaRepository.findByEmail(principa.getName()).orElseThrow();

        if (!animal.getProtectora().getId().equals(cuenta.getProtectora().getId())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este animal");
            return "redirect:/protectora/panel";
        }

        model.addAttribute("animal", animal);
        model.addAttribute("especies", List.of("Perro", "Gato", "Otros"));
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("estados", EstadoAnimal.values());

        return "protectora/formulario-animal";


    }


}
