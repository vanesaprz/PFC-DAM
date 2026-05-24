package com.example.PFC_DAM.repos;

import com.example.PFC_DAM.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {


    List<Notificacion> findByCuentaIdOrderByFechaDesc(Long cuentaId);

    long countByCuentaIdAndLeidaFalse(Long cuentaId);
}
