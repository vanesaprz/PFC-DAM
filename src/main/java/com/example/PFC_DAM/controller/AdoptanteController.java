package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.DTO.AdoptantePerfilDTO;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.service.CloudinaryService;
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

@Controller
@RequestMapping("/adoptante")
@PreAuthorize("hasRole('ADOPTANTE')")
public class AdoptanteController {

    @Autowired
    private AdoptanteRepository adoptanteRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    //Pantalla Mi Perfil
    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal) {

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        AdoptantePerfilDTO dto = new AdoptantePerfilDTO();

        dto.setApellidos(adoptante.getApellidos());
        dto.setNombre(adoptante.getNombre());
        dto.setProvincia(adoptante.getProvincia());
        dto.setPresentacion(adoptante.getPresentacion());
        dto.setFotoPerfil(adoptante.getFotoPerfil());

        model.addAttribute("adoptanteDTO", dto);
        model.addAttribute("cuenta", cuenta);
        return "adoptante/perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@Valid @ModelAttribute("adoptanteDTO") AdoptantePerfilDTO dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                @RequestParam(value = "archivoFoto", required = false) MultipartFile foto,
                                Principal principal) {

        if (result.hasErrors()) {
            return "adoptante/perfil";
        }

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        adoptante.setNombre(dto.getNombre());
        adoptante.setProvincia(dto.getProvincia());
        adoptante.setPresentacion(dto.getPresentacion());

        // Si se sube foto nueva la enviamos a Cloudinary, si no mantenemos la actual
        if (foto != null && !foto.isEmpty()) {
            try {
                String urlFoto = cloudinaryService.subirImagen(foto);
                adoptante.setFotoPerfil(urlFoto);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Error al subir la foto: " + e.getMessage());
                return "redirect:/adoptante/perfil";
            }
        }

        adoptante.setApellidos(dto.getApellidos());

        adoptanteRepository.save(adoptante);
        redirectAttributes.addFlashAttribute("mensaje", "Perfil guardado correctamente");

        return "redirect:/adoptante/perfil";

    }


}
