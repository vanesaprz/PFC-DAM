package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Protectora;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.ProtectoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.PFC_DAM.model.enums.Rol.ADOPTANTE;
import static com.example.PFC_DAM.model.enums.Rol.PROTECTORA;


@Service
public class UsuarioService {

    //PENDIENTE IMPLEMENTAR EL CIFRADO DE CONTRASEÑA

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private AdoptanteRepository adoptanteRepository;
    @Autowired
    private ProtectoraRepository protectoraRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registrarAdoptante(Cuenta cuenta, Adoptante adoptante) throws Exception {
        if (cuentaRepository.existsByEmail(cuenta.getEmail())) {
            throw new Exception("El email ya está registrado");
        }
        //Se encripta la contraseña antes de guardarla en
        cuenta.setContrasena(passwordEncoder.encode(cuenta.getContrasena()));
        cuenta.setRol(ADOPTANTE);
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        adoptante.setCuenta(cuentaGuardada);
        adoptanteRepository.save(adoptante);
    }

    @Transactional
    public void registrarProtectora(Cuenta cuenta, Protectora protectora) throws Exception {
        if (cuentaRepository.existsByEmail(cuenta.getEmail())) {
            throw new Exception("El email ya se ha registrado");
        }
        cuenta.setContrasena(passwordEncoder.encode(cuenta.getContrasena()));
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        cuenta.setRol(PROTECTORA);
        protectora.setCuenta(cuentaGuardada);
        protectoraRepository.save(protectora);
    }
}
