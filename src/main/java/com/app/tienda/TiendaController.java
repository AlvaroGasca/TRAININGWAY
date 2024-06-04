/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tienda;


import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author alvarogasca
 */
@Controller
@RequestMapping("/tienda")
public class TiendaController {
    @Autowired
    private TiendaService tiendaService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mis-productos")
    public String listarProductosPorUsuario(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        List<Tienda> tienda = tiendaService.obtenerProductosPorUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tienda", tienda);
        return "listaProductos";
    }

    @GetMapping("/todos")
    public String listarTodosLosProductos(Model model) {
        List<Tienda> tienda = tiendaService.obtenerTodosLosProductosOrdenadosPorFecha();
        model.addAttribute("tienda", tienda);
        return "listaTodosProductos";
    }

    @GetMapping("/formularioProducto")
    public String mostrarFormularioProducto(Model model, Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tienda", new Tienda());
        return "formularioProducto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("tienda") Tienda tienda, @RequestParam("idUsuario") Long idUsuario) {
        tiendaService.guardarProducto(tienda, idUsuario);
        return "redirect:/tienda/mis-productos";
    }

    @GetMapping("/modificar/{id}")
    public String modificarProductoForm(@PathVariable Long id, Model model) {
        Tienda tienda = tiendaService.obtenerProductoPorId(id);
        if (tienda != null) {
            model.addAttribute("tienda", tienda);
            return "formularioEditarProducto";
        } else {
            return "redirect:/tienda/mis-productos";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modificarProductoSubmit(@PathVariable Long id, @ModelAttribute("tienda") Tienda tienda) {
        Tienda productoExistente = tiendaService.obtenerProductoPorId(id);
        if (productoExistente != null) {
            productoExistente.setEspecialidad(tienda.getEspecialidad());
            productoExistente.setTitulo(tienda.getTitulo());
            productoExistente.setFecha(tienda.getFecha());
            productoExistente.setCuerpo(tienda.getCuerpo());
            productoExistente.setEnlace(tienda.getEnlace());

            tiendaService.guardarProducto(productoExistente, productoExistente.getUsuario().getId());
        }
        return "redirect:/tienda/mis-productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        tiendaService.eliminarProducto(id);
        return "redirect:/tienda/mis-productos";
    }

    @PostMapping("/like/{id}")
    public String likeNProducto(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        tiendaService.incrementarMeGusta(id, usuario.getId());
        return "redirect:/tienda/todos";
    }

    @PostMapping("/dislike/{id}")
    public String dislikeProducto(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        tiendaService.decrementarMeGusta(id, usuario.getId());
        return "redirect:/tienda/todos";
    }
    
    @PostMapping("/contador/{id}")
    public String contadorNProducto(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        tiendaService.incrementarContador(id, usuario.getId());
        return "redirect:/tienda/todos";
    }
}
