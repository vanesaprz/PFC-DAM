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
    public void registrarAdoptante(Cuenta cuenta, Adoptante adoptante) {
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        adoptante.setCuenta(cuentaGuardada);
        adoptanteRepository.save(adoptante);
    }

    @Transactional
    public void registrarProtectora(Cuenta cuenta, Protectora protectora) {
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        protectora.setCuenta(cuentaGuardada);
        protectoraRepository.save(protectora);
    }
}
