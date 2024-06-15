/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.favoritos.Favorito;
import com.app.favoritos.FavoritoService;
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
    
    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/mi-perfil")
    public String perfilPorUsuario(Model model, Principal principal) {
    String username = principal.getName();
    Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
    Perfil perfil = perfilService.obtenerPerfilPorUsuario(usuario);
    if (perfil == null) {
        return "redirect:/perfil/formularioPerfil";
    }
    model.addAttribute("usuario", usuario);
    model.addAttribute("perfil", perfil);
    model.addAttribute("authUserId", usuario.getId());
    return "perfil";
}
    
    @GetMapping("/todos")
    public String listarTodosLosPerfiles(Model model) {
        List<Perfil> perfiles = perfilService.obtenerTodosLosPerfiles();
        model.addAttribute("perfiles", perfiles);
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

    @GetMapping("/ver/{id}")
    public String verPerfil(@PathVariable Long id, Model model, Principal principal) {
    String username = principal.getName();
    Usuario authUser = usuarioService.obtenerUsuarioPorUsername(username);
    Perfil perfil = perfilService.obtenerPerfilPorId(id);
    if (perfil != null) {
        model.addAttribute("perfil", perfil);
        model.addAttribute("authUserId", authUser.getId());
        return "perfil";
    } else {
        return "redirect:/perfil/mi-perfil";
    }
}
    
    @GetMapping("/modificar/{id}")
    public String modificarPerfilForm(@PathVariable Long id, Model model) {
        Perfil perfil = perfilService.obtenerPerfilPorId(id);
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            model.addAttribute("usuario", perfil.getUsuario());
            return "formularioEditarPerfil";
        } else {
            return "redirect:/perfil/mi-perfil";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modificarPerfilSubmit(@PathVariable Long id, @ModelAttribute("perfil") Perfil perfil, @RequestParam String correo, @RequestParam String telefono) {
        Perfil perfilExistente = perfilService.obtenerPerfilPorId(id);
        if (perfilExistente != null) {
             Usuario usuario = perfilExistente.getUsuario();
            usuario.setCorreo(correo);
            usuario.setTelefono(Integer.parseInt(telefono));
            perfilExistente.setDescripcion(perfil.getDescripcion());
            perfilExistente.setDisponibilidad(perfil.getDisponibilidad());
            perfilExistente.setDiasDisponibles(perfil.getDiasDisponibles());
            perfilExistente.setHorarioMananaInicio(perfil.getHorarioMananaInicio());
            perfilExistente.setHorarioMananaFin(perfil.getHorarioMananaFin());
            perfilExistente.setHorarioTardeInicio(perfil.getHorarioTardeInicio());
            perfilExistente.setHorarioTardeFin(perfil.getHorarioTardeFin());
            
            perfilExistente.setMostrarTelefono(perfil.isMostrarTelefono());
        perfilExistente.setMostrarCorreo(perfil.isMostrarCorreo());
            perfilService.guardarPerfil(perfilExistente, perfilExistente.getUsuario().getId());
        }
        return "redirect:/perfil/mi-perfil";
    }
    

@PostMapping("/agregar-favorito/{id}")
    public String agregarAFavoritos(@PathVariable Long id, Principal principal) {
        favoritoService.agregarAFavoritos(id, principal.getName());
        return "redirect:/perfil/favoritos";
    }

    @PostMapping("/eliminar-favorito/{id}")
    public String eliminarDeFavoritos(@PathVariable Long id, Principal principal) {
        favoritoService.eliminarDeFavoritos(id, principal.getName());
        return "redirect:/perfil/favoritos";
    }

    @GetMapping("/favoritos")
    public String listarFavoritos(Model model, Principal principal) {
        model.addAttribute("favoritos", favoritoService.obtenerFavoritosPorUsuario(principal.getName()));
        return "favoritos";
    }
}
