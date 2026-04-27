package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.DTO.RegistroAdoptanteDTO;
import com.example.PFC_DAM.model.DTO.RegistroProtectoraDTO;
import com.example.PFC_DAM.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthViewController {
    //Prototipo de controlador para formulario de adoptante..

    @Autowired
    private UsuarioService usuarioService;

    //Muestra el formulario registro-adoptante.html
    @GetMapping("/registro/adoptante")
    public String mostrarFormularioRegAdoptante(Model model) {
        model.addAttribute("datosAdoptante", new RegistroAdoptanteDTO());
        return "registro-adoptante"; // Nombre del archivo HTML
    }

    //Muestra el formulario registro-protectora.html
    @GetMapping("/registro/protectora")
    public String mostrarFormularioRegProtectora(Model model) {
        model.addAttribute("datosProtectora", new RegistroProtectoraDTO());
        return "registro-protectora";
    }


    // 2. Procesa los datos del formulario
    @PostMapping("/registro/adoptante")
    public String registrarAdoptante(@Valid @ModelAttribute("datosAdoptante") RegistroAdoptanteDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro-adoptante";
        }
        try {
            usuarioService.registrarAdoptante(dto);
            return "redirect:/login?exito";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro-adoptante";
        }
    }

    @PostMapping("/registro/protectora")
    public String registrarProtectora(@Valid @ModelAttribute("datosProtectora") RegistroProtectoraDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro-protectora";
        }
        try {
            usuarioService.registrarProtectora(dto);
            return "redirect:/login?exito";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro-protectora";
        }

    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Esto buscará el archivo login.html en templates
    }
}

