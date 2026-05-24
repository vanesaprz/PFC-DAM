package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Notificacion;
import com.example.PFC_DAM.repos.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public void crear(Cuenta cuenta, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setCuenta(cuenta);
        notificacion.setMensaje(mensaje);
        notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerPorCuenta(Long cuentaId) {
        return notificacionRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    public long contarNoLeidas(Long cuentaId) {
        return notificacionRepository.countByCuentaIdAndLeidaFalse(cuentaId);
    }

    public void marcarComoLeida(Long notificacionId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId).orElseThrow();
        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }

    public void marcarTodasComoLeidas(Long cuentaId) {
        List<Notificacion> notificaciones = notificacionRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
        notificaciones.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(notificaciones);
    }
}