package com.example.PFC_DAM.controller;

import com.example.PFC_DAM.model.Adoptante;
import com.example.PFC_DAM.model.Cuenta;
import com.example.PFC_DAM.model.Favorito;
import com.example.PFC_DAM.repos.AdoptanteRepository;
import com.example.PFC_DAM.repos.AnimalRepository;
import com.example.PFC_DAM.repos.CuentaRepository;
import com.example.PFC_DAM.repos.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/adoptante/favoritos")
@PreAuthorize("hasAnyRole('ADOPTANTE', 'ADMIN')")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    //Pantalla favoritos
    @GetMapping("")
    public String verFavoritos(Model model, Principal principal) {

        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        List<Favorito> favoritos = favoritoRepository.findByAdoptanteId(adoptante.getId());
        model.addAttribute("favoritos", favoritos);

        return "adoptante/favoritos";
    }

    //
    @PostMapping("/agregar/{animalId}")
    public String agregarFavorito(@PathVariable Long animalId, Principal principal, RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        Favorito favorito = new Favorito();
        favorito.setAdoptante(adoptante);
        favorito.setAnimal(animalRepository.getReferenceById(animalId));
        favoritoRepository.save(favorito);

        redirectAttributes.addFlashAttribute("mensaje", "Animal añadido a favoritos");
        return "redirect:/animales/" + animalId;

    }

    //Para eliminar favoritos
    @PostMapping("/eliminar/{animalId}")
    public String eliminarFavoritos(@PathVariable Long animalId, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaRepository.findByEmail(principal.getName()).orElseThrow();
        Adoptante adoptante = adoptanteRepository.findByCuenta(cuenta).orElseThrow();

        favoritoRepository.findByAdoptanteIdAndAnimalId(adoptante.getId(), animalId).ifPresent(favoritoRepository::delete);

        redirectAttributes.addFlashAttribute("mensaje", "Animal eliminado de favoritos");

        return "redirect:/adoptante/favoritos";
    }
}
