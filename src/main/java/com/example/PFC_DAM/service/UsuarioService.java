package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Protectora;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.ProtectoraRepository;
import org.springframework.transaction.annotation.Transactional;


public class UsuarioService {

    //PENDIENTE IMPLEMENTAR EL CIFRADO DE CONTRASEÑA

    private CuentaRepository cuentaRepository;
    private AdoptanteRepository adoptanteRepository;
    private ProtectoraRepository protectoraRepository;

    @Transactional
    public void registrarAdoptante(Cuenta cuenta, Adoptante adoptante) throws Exception {
        if (cuentaRepository.existsByEmail(cuenta.getEmail())) {
            throw new Exception("El email ya está registrado");
        }
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        adoptante.setCuenta(cuentaGuardada);
        adoptanteRepository.save(adoptante);
    }

    @Transactional
    public void registrarProtectora(Cuenta cuenta, Protectora protectora) throws Exception {
        if (cuentaRepository.existsByEmail(cuenta.getEmail())) {
            throw new Exception("El email ya se ha registrado");
        }
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        protectora.setCuenta(cuentaGuardada);
        protectoraRepository.save(protectora);
    }
}
