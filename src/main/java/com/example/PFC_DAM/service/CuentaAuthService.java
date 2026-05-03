package com.example.PFC_DAM.service;

import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.repos.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CuentaAuthService implements UserDetailsService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos la cuenta en la BD
        Cuenta cuenta = cuentaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con email: " + email));

        // Devolvemos el objeto User que Spring Security sabe gestionar
        System.out.println("Login intentando: " + cuenta.getEmail() + " con Rol: " + cuenta.getRol().name());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("¿Coincide admin123?: " + encoder.matches("admin123", cuenta.getContrasena()));
        return User.builder()
                .username(cuenta.getEmail())
                .password(cuenta.getContrasena()) // La contraseña ya encriptada
                .roles(cuenta.getRol().name())    // ADOPTANTE, PROTECTORA o ADMIN
                .build();
    }

}

