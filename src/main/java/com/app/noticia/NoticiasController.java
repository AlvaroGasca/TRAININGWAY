/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.noticia;

import com.app.usuario.UsuarioService;
import com.app.usuario.Usuario;
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
@RequestMapping("/noticias")
public class NoticiasController {

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mis-noticias")
    public String listarNoticiasPorUsuario(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        List<Noticia> noticias = noticiaService.obtenerNoticiasPorUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("noticias", noticias);
        return "listaNoticias";
    }
  
    @GetMapping("/formularioNoticia")
    public String mostrarFormularioNoticia(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("noticia", new Noticia());
        return "formularioNoticia";
    }

   
    @PostMapping("/guardar")
    public String guardarNoticia(@ModelAttribute("noticia") Noticia noticia, @RequestParam("idUsuario") Long idUsuario) {
         noticiaService.guardarNoticia(noticia, idUsuario);
        return "redirect:/noticias/mis-noticias";  
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarNoticiaForm(@PathVariable Long id, Model model) {
    Noticia noticia = noticiaService.obtenerNoticiaPorId(id);
    if (noticia != null) {
        model.addAttribute("noticia", noticia);
        return "formularioEditarNoticia";
    } else {
        return "redirect:/noticias/mis-noticias";
    }
}


    @PostMapping("/modificar/{id}")
    public String modificarNoticiaSubmit(@PathVariable Long id, @ModelAttribute("noticia") Noticia noticia) {
    Noticia noticiaExistente = noticiaService.obtenerNoticiaPorId(id);
    if (noticiaExistente != null) {
        noticiaExistente.setDeporte(noticia.getDeporte());
        noticiaExistente.setTitulo(noticia.getTitulo());
        noticiaExistente.setFecha(noticia.getFecha());
        noticiaExistente.setCuerpo(noticia.getCuerpo()); 

        noticiaService.guardarNoticia(noticiaExistente, noticiaExistente.getUsuario().getId());
    }
    return "redirect:/noticias/mis-noticias";
}



    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable Long id) {
        noticiaService.eliminarNoticia(id);
        return "redirect:/noticias/mis-noticias";
    }

}

