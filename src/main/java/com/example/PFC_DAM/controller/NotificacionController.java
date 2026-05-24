package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Notificacion;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping
    public String verNotificaciones(Model model, Principal principal) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        List<Notificacion> notificaciones = notificacionService.obtenerPorCuenta(cuenta.getId());
        notificacionService.marcarTodasComoLeidas(cuenta.getId());
        model.addAttribute("notificaciones", notificaciones);
        return "notificaciones";
    }
}