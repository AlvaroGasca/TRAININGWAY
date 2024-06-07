/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
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
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mi-perfil")
    public String perfilPorUsuario(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        Perfil perfil = perfilService.obtenerPerfilPorUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("perfil", perfil);
        return "perfil";
    }
    
    @GetMapping("/todos")
    public String listarTodosLosPerfiles(Model model) {
        List<Perfil> perfil = perfilService.obtenerTodosLosPerfiles();
        model.addAttribute("perfil", perfil);
        return "listaPerfiles";
    }
  
    @GetMapping("/formularioPerfil")
    public String mostrarFormularioPerfil(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("perfil", new Perfil());
        return "formularioPerfil";
    }

    @PostMapping("/guardar")
    public String guardarPerfil(@ModelAttribute("perfil") Perfil perfil, @RequestParam("idUsuario") Long idUsuario) {
        perfilService.guardarPerfil(perfil, idUsuario);
        return "redirect:/perfil/mi-perfil";  
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarPerfilForm(@PathVariable Long id, Model model) {
        Perfil perfil = perfilService.obtenerPerfilPorId(id);
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            return "formularioEditarPerfil";
        } else {
            return "redirect:/perfil/mi-perfil";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modificarPerfilSubmit(@PathVariable Long id, @ModelAttribute("perfil") Perfil perfil) {
        Perfil perfilExistente = perfilService.obtenerPerfilPorId(id);
        if (perfilExistente != null) {
            perfilExistente.setDescripcion(perfil.getDescripcion());
            perfilExistente.setDisponibilidad(perfil.getDisponibilidad());
            perfilService.guardarPerfil(perfilExistente, perfilExistente.getUsuario().getId());
        }
        return "redirect:/perfil/mi-perfil";
    }

    @GetMapping("/ver/{id}")
    public String verPerfil(@PathVariable Long id, Model model) {
        Perfil perfil = perfilService.obtenerPerfilPorId(id);
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            return "perfil";
        } else {
            return "redirect:/perfil/mi-perfil";
        }
    }


}