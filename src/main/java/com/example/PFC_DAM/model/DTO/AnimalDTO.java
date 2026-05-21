package com.example.PFC_DAM.model.DTO;

import com.example.PFC_DAM.model.enums.Especie;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.Sexo;
import com.example.PFC_DAM.model.enums.Tamano;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AnimalDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La especie es obligatoria")
    private Especie especie;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "Indicar el sexo del animal es obligatorio")
    private Sexo sexo;

    @NotNull(message = "Indica la fecha aproximada de nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull(message = "La fecha de ingreso en la protectora es obligatoria")
    private LocalDate fechaIngreso;

    @NotNull(message = "El estado es obligatorio")
    private EstadoAnimal estado;

    @NotNull(message = "El tamaño es obligatorio")
    private Tamano tamano;

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0")
    private BigDecimal peso;

    private String fotoPrincipal;

    private Boolean esterilizado = false;
    private Boolean vacunado = false;
    private Boolean desparasitado = false;
    private Boolean microchip = false;
    private Boolean aptoPerros = false;
    private Boolean aptoGatos = false;
    private Boolean aptoNinos = false;

    private String necesidadesEspeciales;
    @NotBlank(message = "El nivel de actividad es obligatorio")
    private String nivelActividad;
    private String miedos;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}
