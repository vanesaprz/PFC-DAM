package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.*;
import com.example.PFC_DAM.model.DTO.AnimalDTO;
import com.example.PFC_DAM.model.DTO.ProtectoraPerfilDTO;
import com.example.PFC_DAM.model.enums.*;
import com.example.PFC_DAM.repos.*;
import com.example.PFC_DAM.service.AnimalService;
import com.example.PFC_DAM.service.CloudinaryService;
import com.example.PFC_DAM.service.SolicitudService;
import com.example.PFC_DAM.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
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

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/panel")
    public String mostrarPanel(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElse(null);
        Protectora protectora = cuenta.getProtectora();

        //Para las tarjetas de administración:
        model.addAttribute("cantEnAdopcion", animalRepository.countByProtectoraIdAndEstadoNot(protectora.getId(), EstadoAnimal.ADOPTADO));
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

    @PostMapping("/animales/guardar")
    public String guardarAnimal(@Valid @ModelAttribute("animalDTO") AnimalDTO dto,
                                BindingResult result,
                                @RequestParam(value = "archivoFoto", required = false) MultipartFile foto,
                                Principal principal,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (dto.getFechaNacimiento() != null && dto.getFechaNacimiento().isAfter(LocalDate.now())) {
            result.rejectValue("fechaNacimiento", "error.fechaNacimiento", "La fecha de nacimiento no puede ser futura");
        }
        if (dto.getFechaIngreso() != null && dto.getFechaIngreso().isAfter(LocalDate.now())) {
            result.rejectValue("fechaIngreso", "error.fechaIngreso", "La fecha de ingreso no puede ser futura");
        }
        if (dto.getFechaNacimiento() != null && dto.getFechaIngreso() != null
                && dto.getFechaIngreso().isBefore(dto.getFechaNacimiento())) {
            result.rejectValue("fechaIngreso", "error.fechaIngreso", "La fecha de ingreso no puede ser anterior a la fecha de nacimiento");
        }

        boolean faltaFoto = (foto == null || foto.isEmpty()) && dto.getId() == null;

        if (result.hasErrors() || faltaFoto) {
            model.addAttribute("especies", Especie.values());
            model.addAttribute("sexos", Sexo.values());
            model.addAttribute("estados", EstadoAnimal.values());
            model.addAttribute("tamanos", Tamano.values());
            if (faltaFoto) {
                model.addAttribute("errorFoto", "La foto principal es obligatoria");
            }
            return "protectora/formulario-animal";
        }

        try {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
            animalService.guardarAnimal(dto, foto, cuenta);
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
        try {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
            animalService.borrarAnimal(id, cuenta);
            redirectAttributes.addFlashAttribute("mensaje", "Animal eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
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
        solicitudService.actualizarEstado(id, estado, notas);
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud actualizada correctamente");
        return "redirect:/protectora/solicitudes";
    }

    //Mostrar la pagina que contiene los datos del perfil:
    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectora = cuenta.getProtectora();

        // Cargamos el DTO con los datos actuales de la protectora
        ProtectoraPerfilDTO dto = new ProtectoraPerfilDTO();
        dto.setNombre(protectora.getNombre());
        dto.setDireccion(protectora.getDireccion());
        dto.setTelefono(protectora.getTelefono());
        dto.setProvincia(protectora.getProvincia());
        dto.setEmailContacto(protectora.getEmailContacto());
        dto.setWeb(protectora.getWeb());
        dto.setPresentacion(protectora.getPresentacion());

        model.addAttribute("protectoraDTO", dto);
        model.addAttribute("protectora", protectora);
        model.addAttribute("menuActivo", "perfil");
        model.addAttribute("tiposRedSocial", List.of("instagram", "facebook", "twitter-x", "whatsapp", "youtube", "tiktok", "telegram"));

        return "protectora/perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@Valid @ModelAttribute("protectoraDTO") ProtectoraPerfilDTO dto,
                                BindingResult result,
                                @RequestParam(value = "archivoLogo", required = false) MultipartFile logo,
                                Principal principal,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
            model.addAttribute("protectora", cuenta.getProtectora());
            model.addAttribute("menuActivo", "perfil");
            model.addAttribute("tiposRedSocial", List.of("instagram", "facebook", "twitter-x", "whatsapp", "youtube", "tiktok", "telegram"));
            return "protectora/perfil";
        }

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Protectora protectoraActual = cuenta.getProtectora();

        protectoraActual.setNombre(dto.getNombre());
        protectoraActual.setDireccion(dto.getDireccion());
        protectoraActual.setTelefono(dto.getTelefono());
        protectoraActual.setProvincia(dto.getProvincia());
        protectoraActual.setPresentacion(dto.getPresentacion());
        protectoraActual.setEmailContacto(dto.getEmailContacto());
        protectoraActual.setWeb(dto.getWeb());

        // Si se sube un logo nuevo lo subimos a Cloudinary, si no mantenemos el actual
        if (logo != null && !logo.isEmpty()) {
            try {
                String urlLogo = cloudinaryService.subirImagen(logo);
                protectoraActual.setLogo(urlLogo);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Error al subir el logo: " + e.getMessage());
                return "redirect:/protectora/perfil";
            }
        }

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

    @PostMapping("/perfil/eliminar")
    public String eliminarCuentaProtectora(Principal principal, HttpServletRequest request) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        usuarioService.eliminarProtectora(cuenta);
        request.getSession().invalidate();
        return "redirect:/";
    }


}
