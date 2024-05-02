/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usuariosCoches;

import com.usuariosCoches.usuario.UsuarioService;
import com.usuariosCoches.usuario.Usuario;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author alvarogasca
 */
@Controller
@RequestMapping("/coches")
public class CocheController {

    @Autowired
    private CocheService cocheService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mis-coches")
    public String listarCochesPorUsuario(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        List<Coche> coches = cocheService.obtenerCochesPorUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("coches", coches);
        return "listaCoches";
    }
  
    @GetMapping("/formularioCoche")
    public String mostrarFormularioCoche(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("coche", new Coche());
        return "formularioCoche";
    }

   
    @PostMapping("/guardar")
    public String guardarCoche(@ModelAttribute("coche") Coche coche, @RequestParam("idUsuario") Long idUsuario) {
         cocheService.guardarCoche(coche, idUsuario);
        return "redirect:/coches/mis-coches";  
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarCocheForm(@PathVariable Long id, Model model) {
    Coche coche = cocheService.obtenerCochePorId(id);
    if (coche != null) {
        model.addAttribute("coche", coche);
        return "formularioEditarCoche";
    } else {
        return "redirect:/coches/mis-coches";
    }
}


    @PostMapping("/modificar/{id}")
    public String modificarCocheSubmit(@PathVariable Long id, @ModelAttribute("coche") Coche coche) {
    Coche cocheExistente = cocheService.obtenerCochePorId(id);
    if (cocheExistente != null) {
        cocheExistente.setMarca(coche.getMarca());
        cocheExistente.setModelo(coche.getModelo());
        cocheExistente.setAnnos(coche.getAnnos());
        cocheExistente.setMatricula(coche.getMatricula()); 

        cocheService.guardarCoche(cocheExistente, cocheExistente.getUsuario().getId());
    }
    return "redirect:/coches/mis-coches";
}



    @GetMapping("/eliminar/{id}")
    public String eliminarCoche(@PathVariable Long id) {
        cocheService.eliminarCoche(id);
        return "redirect:/coches/mis-coches";
    }

}

