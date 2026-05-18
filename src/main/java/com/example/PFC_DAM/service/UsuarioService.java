package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.DTO.RegistroAdoptanteDTO;
import com.example.PFC_DAM.model.DTO.RegistroProtectoraDTO;
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
    
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private AdoptanteRepository adoptanteRepository;
    @Autowired
    private ProtectoraRepository protectoraRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registrarAdoptante(RegistroAdoptanteDTO dto) throws Exception {
        if (cuentaRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("El email ya está registrado");
        }
        //Se encripta la contraseña antes de guardarla en base de datos, solo si los dos campos son iguales
        if (!dto.getPassword().equals(dto.getRepetirPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }

        //Creamos cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setRol(ADOPTANTE);
        cuenta.setEmail(dto.getEmail());
        cuenta.setContrasena(passwordEncoder.encode(dto.getPassword()));
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        //Creamos adoptante
        Adoptante adoptante = new Adoptante();
        adoptante.setNombre(dto.getNombre());
        adoptante.setApellidos(dto.getApellidos());
        adoptante.setProvincia(dto.getProvincia());
        adoptante.setPresentacion(dto.getPresentacion());
        adoptante.setFotoPerfil(dto.getFoto_perfil());
        adoptante.setCuenta(cuentaGuardada);
        adoptanteRepository.save(adoptante);

    }

    @Transactional
    public void registrarProtectora(RegistroProtectoraDTO dto) throws Exception {
        if (cuentaRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("El email ya se ha registrado");
        }
        //Se encripta la contraseña antes de guardarla en base de datos, solo si los dos campos son iguales
        if (!dto.getPassword().equals(dto.getRepetirPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setEmail(dto.getEmail());
        cuenta.setContrasena(passwordEncoder.encode(dto.getPassword()));
        cuenta.setRol(PROTECTORA);
        cuenta = cuentaRepository.save(cuenta);

        Protectora protectora = new Protectora();
        protectora.setNombre(dto.getNombre());
        protectora.setProvincia(dto.getProvincia());
        protectora.setPresentacion(dto.getPresentacion());
        protectora.setCif(dto.getCif());
        protectora.setDireccion(dto.getDireccion());
        protectora.setTelefono(dto.getTelefono());
        protectora.setEmailContacto(dto.getEmailContacto());
        protectora.setLogo(dto.getLogo());
        protectora.setCuenta(cuenta);

        protectoraRepository.save(protectora);
    }
}
