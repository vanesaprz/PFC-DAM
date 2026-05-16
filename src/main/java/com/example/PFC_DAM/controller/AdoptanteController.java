package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.DTO.AdoptantePerfilDTO;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/adoptante")
@PreAuthorize("hasRole('ADOPTANTE')")
public class AdoptanteController {

    @Autowired
    private AdoptanteRepository adoptanteRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

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
                                Principal principal) {

        if (result.hasErrors()) {
            return "adoptante/perfil";
        }

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        adoptante.setNombre(dto.getNombre());
        adoptante.setProvincia(dto.getProvincia());
        adoptante.setPresentacion(dto.getPresentacion());
        adoptante.setFotoPerfil(dto.getFotoPerfil());
        adoptante.setApellidos(dto.getApellidos());

        adoptanteRepository.save(adoptante);
        redirectAttributes.addFlashAttribute("message", "Perfil guardado correctamente");

        return "redirect:/adoptante/perfil";

    }


}
