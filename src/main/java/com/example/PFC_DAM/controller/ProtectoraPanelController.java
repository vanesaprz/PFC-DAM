package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.*;
import com.example.PFC_DAM.model.DTO.AnimalDTO;
import com.example.PFC_DAM.model.enums.*;
import com.example.PFC_DAM.repos.*;
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

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private ProtectoraRepository protectoraRepository;

    @Autowired
    private RedSocialRepository redSocialRepository;

    @GetMapping("/panel")
    public String mostrarPanel(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElse(null);
        Protectora protectora = cuenta.getProtectora();

        //Para las tarjetas de administración:
        model.addAttribute("cantEnAdopcion", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.DISPONIBLE));
        model.addAttribute("cantAdoptados", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.ADOPTADO));
        model.addAttribute("cantUrgentes", animalRepository.countByProtectoraIdAndEstado(protectora.getId(), EstadoAnimal.URGENTE));
        model.addAttribute("cantSolPendientes", solicitudRepository.countByAnimalProtectoraIdAndEstado(protectora.getId(), EstadoSolicitud.PENDIENTE));


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
        AnimalDTO animalDTO = new AnimalDTO();
        //Valores por defecto:
        animalDTO.setVacunado(false);
        animalDTO.setEsterilizado(false);
        animalDTO.setDesparasitado(false);
        animalDTO.setMicrochip(false);
        animalDTO.setAptoPerros(false);
        animalDTO.setAptoGatos(false);
        animalDTO.setAptoNinos(false);
        animalDTO.setEstado(EstadoAnimal.DISPONIBLE);

        model.addAttribute("animalDTO", animalDTO);

        model.addAttribute("especies", Especie.values());
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("estados", EstadoAnimal.values());
        model.addAttribute("tamanos", Tamano.values());

        return "protectora/formulario-animal";
    }

    //Guardar formulario
    @PostMapping("/animales/guardar")
    public String guardarAnimal(@Valid @ModelAttribute("animalDTO") AnimalDTO dto,
                                BindingResult result,
                                Principal principal,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Volvemos a cargar las listas necesarias para el formulario
            model.addAttribute("especies", Especie.values());
            model.addAttribute("sexos", Sexo.values());
            model.addAttribute("estados", EstadoAnimal.values());
            model.addAttribute("tamanos", Tamano.values());

            return "protectora/formulario-animal"; // Se queda en la misma página mostrando errores
        }
        try {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

            Animal animal;
            if (dto.getId() != null) {
                animal = animalRepository.findById(dto.getId()).orElseThrow();
            } else {
                animal = new Animal();
            }

            animal.setNombre(dto.getNombre());
            animal.setEspecie(dto.getEspecie());
            animal.setRaza(dto.getRaza());
            animal.setSexo(dto.getSexo());
            animal.setFechaNacimiento(dto.getFechaNacimiento());
            animal.setFechaIngreso(dto.getFechaIngreso());
            animal.setEstado(dto.getEstado());
            animal.setTamano(dto.getTamano());
            animal.setPeso(dto.getPeso());
            animal.setFotoPrincipal(dto.getFotoPrincipal());
            animal.setEsterilizado(dto.getEsterilizado() != null ? dto.getEsterilizado() : false);
            animal.setVacunado(dto.getVacunado() != null ? dto.getVacunado() : false);
            animal.setDesparasitado(dto.getDesparasitado() != null ? dto.getDesparasitado() : false);
            animal.setMicrochip(dto.getMicrochip() != null ? dto.getMicrochip() : false);
            animal.setAptoPerros(dto.getAptoPerros() != null ? dto.getAptoPerros() : false);
            animal.setAptoGatos(dto.getAptoGatos() != null ? dto.getAptoGatos() : false);
            animal.setAptoNinos(dto.getAptoNinos() != null ? dto.getAptoNinos() : false);
            animal.setNecesidadesEspeciales(dto.getNecesidadesEspeciales());
            animal.setNivelActividad(dto.getNivelActividad());
            animal.setMiedos(dto.getMiedos());
            animal.setDescripcion(dto.getDescripcion());
            animal.setProtectora(cuenta.getProtectora());


            animalRepository.save(animal);
            redirectAttributes.addFlashAttribute("mensaje", "¡Animal guardado con éxito!");
            return "redirect:/protectora/panel";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/protectora/animales/nuevo";
        }
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
    public String formularioEditarAnimal(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();

        if (!animal.getProtectora().getId().equals(cuenta.getProtectora().getId())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este animal");
            return "redirect:/protectora/panel";
        }
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setId(animal.getId());
        animalDTO.setNombre(animal.getNombre());
        animalDTO.setEspecie(animal.getEspecie());
        animalDTO.setRaza(animal.getRaza());
        animalDTO.setSexo(animal.getSexo());
        animalDTO.setFechaNacimiento(animal.getFechaNacimiento());
        animalDTO.setFechaIngreso(animal.getFechaIngreso());
        animalDTO.setEstado(animal.getEstado());
        animalDTO.setTamano(animal.getTamano());
        animalDTO.setPeso(animal.getPeso());
        animalDTO.setFotoPrincipal(animal.getFotoPrincipal());
        animalDTO.setEsterilizado(animal.getEsterilizado());
        animalDTO.setVacunado(animal.getVacunado());
        animalDTO.setDesparasitado(animal.getDesparasitado());
        animalDTO.setMicrochip(animal.getMicrochip());
        animalDTO.setAptoPerros(animal.getAptoPerros());
        animalDTO.setAptoGatos(animal.getAptoGatos());
        animalDTO.setAptoNinos(animal.getAptoNinos());
        animalDTO.setNecesidadesEspeciales(animal.getNecesidadesEspeciales());
        animalDTO.setNivelActividad(animal.getNivelActividad());
        animalDTO.setMiedos(animal.getMiedos());
        animalDTO.setDescripcion(animal.getDescripcion());


        model.addAttribute("animalDTO", animalDTO);
        model.addAttribute("especies", Especie.values());
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("estados", EstadoAnimal.values());
        model.addAttribute("tamanos", Tamano.values());

        return "protectora/formulario-animal";
    }

    @GetMapping("/solicitudes")
    public String verSolicitudesRecibidas(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectora = cuenta.getProtectora();

        List<Solicitud> solicitudes = solicitudRepository.findByAnimalProtectoraIdAndEstadoNot(protectora.getId(), EstadoSolicitud.RECHAZADA);
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("menuActivo", "solicitudes");
        return "protectora/solicitudes-recibidas";
    }

    //Para cambiar estado de la solicitud y escribir notas al respecto
    @PostMapping("/solicitudes/actualizar/{id}")
    public String actualizarSolicitud(@PathVariable Long id,
                                      @RequestParam EstadoSolicitud estado,
                                      @RequestParam(required = false) String notas,
                                      RedirectAttributes redirectAttributes) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow();
        solicitud.setEstado(estado);
        solicitud.setNotas(notas);
        solicitudRepository.save(solicitud);
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud actualizada correctamente");
        return "redirect:/protectora/solicitudes";
    }

    //Mostrar la pagina que contiene los datos del perfil:
    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectora = cuenta.getProtectora();

        model.addAttribute("protectora", protectora);
        model.addAttribute("menuActivo", "perfil");
        model.addAttribute("tiposRedSocial", List.of("instagram", "facebook", "twitter-x", "whatsapp", "youtube", "tiktok", "telegram"));

        return "protectora/perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Protectora protectora, Principal principal, RedirectAttributes redirectAttributes) {

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectoraActual = cuenta.getProtectora();
        //diferencio entre la protectora de la base de datos "protectoraActual" y la del formulario/modelo "protectora"
        //no puedo guardar como protectoraRepository.save(protectora) porque hay datos que no van a estar en el formulario y  puenden machacarse y dar error
        protectoraActual.setNombre(protectora.getNombre());
        protectoraActual.setDireccion(protectora.getDireccion());
        protectoraActual.setTelefono(protectora.getTelefono());
        protectoraActual.setProvincia(protectora.getProvincia());
        protectoraActual.setPresentacion(protectora.getPresentacion());
        protectoraActual.setLogo(protectora.getLogo());
        protectoraActual.setEmailContacto(protectora.getEmailContacto());

        protectoraRepository.save(protectoraActual);
        redirectAttributes.addFlashAttribute("mensaje", "Perfil guardado correctamente");
        return "redirect:/protectora/perfil";

    }

    //Para añadir nuevas redes sociales a la base de datos de la protectora
    @PostMapping("/perfil/redes/guardar")
    public String guardarRedSocial(@RequestParam String tipo,
                                   @RequestParam String enlaceUsuario,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectora = cuenta.getProtectora();

        RedSocial redSocial = new RedSocial();
        redSocial.setTipo(tipo);
        redSocial.setEnlaceUsuario(enlaceUsuario);
        redSocial.setProtectora(protectora);

        redSocialRepository.save(redSocial);
        redirectAttributes.addFlashAttribute("mensaje", "Red Social añadida correctamente");
        return "redirect:/protectora/perfil";
    }


}
