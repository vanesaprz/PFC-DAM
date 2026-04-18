package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthViewController {
    //Prototipo de controlador para formulario de adoptante..

    @Autowired
    private UsuarioService usuarioService;

    //Muestra el formulario registro-adoptante.html
    @GetMapping("/registro/adoptante")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        model.addAttribute("adoptante", new Adoptante());
        return "registro-adoptante"; // Nombre del archivo HTML
    }

    // 2. Procesa los datos del formulario
    @PostMapping("/registro/adoptante")
    public String registrarAdoptante(Cuenta cuenta, Adoptante adoptante, Model model) {
        try {
            //aqui le paso la cuenta y el adoptanet para que lo registr en la base de datos..
            usuarioService.registrarAdoptante(cuenta, adoptante);
            return "redirect:/login?exito";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cuenta", cuenta);
            model.addAttribute("adoptante", adoptante);
            return "registro-adoptante";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Esto buscará el archivo login.html en templates
    }
}

