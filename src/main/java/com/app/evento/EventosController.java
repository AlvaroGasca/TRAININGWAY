/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.evento;

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
@RequestMapping("/eventos")
public class EventosController {
    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mis-eventos")
    public String listarEventosPorUsuario(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        List<Evento> eventos = eventoService.obtenerEventosPorUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("eventos", eventos);
        return "listaEventos";
    }
    
    @GetMapping("/todos")
    public String listarTodosLosEventos(Model model) {
        List<Evento> eventos = eventoService.obtenerTodosLosEventosOrdenadosPorFecha();
        model.addAttribute("eventos", eventos);
        return "listaTodosEventos";
    }
  
    @GetMapping("/formularioEvento")
    public String mostrarFormularioEvento(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("evento", new Evento());
        return "formularioEvento";
    }

   
    @PostMapping("/guardar")
    public String guardarNoticia(@ModelAttribute("evento") Evento evento, @RequestParam("idUsuario") Long idUsuario) {
         eventoService.guardarEvento(evento, idUsuario);
        return "redirect:/eventos/mis-eventos";  
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarEventoForm(@PathVariable Long id, Model model) {
    Evento evento = eventoService.obtenerEventoPorId(id);
    if (evento != null) {
        model.addAttribute("evento", evento);
        return "formularioEditarEvento";
    } else {
        return "redirect:/eventos/mis-eventos";
    }
}


    @PostMapping("/modificar/{id}")
    public String modificarEventoSubmit(@PathVariable Long id, @ModelAttribute("evento") Evento evento) {
    Evento eventoExistente = eventoService.obtenerEventoPorId(id);
    if (eventoExistente != null) {
        eventoExistente.setEspecialidad(evento.getEspecialidad());
        eventoExistente.setTitulo(evento.getTitulo());
        eventoExistente.setFecha(evento.getFecha());
        eventoExistente.setCuerpo(evento.getCuerpo()); 

        eventoService.guardarEvento(eventoExistente, eventoExistente.getUsuario().getId());
    }
    return "redirect:/eventos/mis-eventos";
}



    @GetMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return "redirect:/eventos/mis-eventos";
    }
    
    @PostMapping("/like/{id}")
    public String likeNEvento(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        eventoService.incrementarMeGusta(id, usuario.getId());
        return "redirect:/eventos/todos";
    }

    @PostMapping("/dislike/{id}")
    public String dislikeEvento(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        eventoService.decrementarMeGusta(id, usuario.getId());
        return "redirect:/eventos/todos";
    }
}
