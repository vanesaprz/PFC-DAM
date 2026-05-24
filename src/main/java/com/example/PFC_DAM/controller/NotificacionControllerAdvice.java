package com.example.PFC_DAM.controller;


import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class NotificacionControllerAdvice {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @ModelAttribute
    public void añadirNotificaciones(Model model, Principal principal) {
        if (principal != null) {
            cuentaRepository.findByEmail(principal.getName()).ifPresent(cuenta -> {
                long noLeidas = notificacionRepository.countByCuentaIdAndLeidaFalse(cuenta.getId());
                model.addAttribute("notificacionesNoLeidas", noLeidas);
            });
        }
    }
}