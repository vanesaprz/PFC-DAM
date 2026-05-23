package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Animal;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.enums.Especie;
import com.example.PFC_DAM.model.enums.Tamano;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/animales")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @GetMapping("")
    public String listarAnimales(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String tamano,
            @RequestParam(required = false) String edad,
            Model model) {

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        LocalDate hoy = LocalDate.now();
        //Calculo los rangos de fechas correspondientes a Cachorro, Joven, etc..

        if (edad != null && !edad.isEmpty()) {
            switch (edad) {
                case "Cachorro":
                    //de 0 a 1 año
                    fechaInicio = hoy.minusYears(1);
                    fechaFin = hoy;
                    break;

                case "Joven":
                    //De 1 a 3 años
                    fechaInicio = hoy.minusYears(3);
                    fechaFin = hoy.minusYears(1).minusDays(1);
                    break;
                case "Adulto":
                    //De 3 a 7 años
                    fechaInicio = hoy.minusYears(7);
                    fechaFin = hoy.minusYears(3).minusDays(1);
                    break;

                case "Senior":
                    fechaInicio = hoy.minusYears(100);
                    fechaFin = hoy.minusYears(7).minusDays(1);
                    break;
            }
        }
        Especie e = (especie != null && !especie.isEmpty()) ? Especie.valueOf(especie) : null;
        String p = (provincia != null && !provincia.isEmpty()) ? provincia : null;
        Tamano t = (tamano != null && !tamano.isEmpty()) ? Tamano.valueOf(tamano) : null;

        List<Animal> animales = animalRepository.buscarConFiltros(e, p, t, fechaInicio, fechaFin);
        model.addAttribute("animales", animales);
        model.addAttribute("especies", Especie.values());
        model.addAttribute("tamanos", Tamano.values());


        //Para poder conseguir que los filtros se mantengan despues de la busqueda
        model.addAttribute("especieSeleccionada", especie);
        model.addAttribute("provinciaSeleccionada", provincia);
        model.addAttribute("tamanoSeleccionado", tamano);
        model.addAttribute("edadSeleccionada", edad);


        return "animales";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model, Principal principal) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal no encontrado"));
        model.addAttribute("animal", animal);

        model.addAttribute("esFavorito", false); // valor por defecto

        if (principal != null) {
            Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
            Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElse(null);
            if (adoptante != null) {
                boolean esFavorito = favoritoRepository.existsByAdoptanteIdAndAnimalId(adoptante.getId(), id);
                model.addAttribute("esFavorito", esFavorito);
            }
        }
        return "detalles";
    }

    @GetMapping("/{id}/adoptar")
    public String formularioAdopcion(@PathVariable Long id, Model model) {
        return "formulario-adopcion";
    }
}
