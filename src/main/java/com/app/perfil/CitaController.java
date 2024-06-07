/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author alvarogasca
 */
@Controller
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/agendar/{perfilId}")
    public String mostrarFormularioCita(@PathVariable Long perfilId, Model model) {
        Perfil perfil = perfilService.obtenerPerfilPorId(perfilId);
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            List<String> horarios = Arrays.asList("08:00", "09:00", "10:00", "11:00", "16:00", "17:00", "18:00");
            model.addAttribute("horarios", horarios);
            return "formularioCita";
        } else {
            return "redirect:/perfil/todos";
        }
    }

    @PostMapping("/agendar")
    public String agendarCita(@RequestParam("perfilId") Long perfilId, @RequestParam("hora") String hora, Principal principal) {
        Perfil perfil = perfilService.obtenerPerfilPorId(perfilId);
        if (perfil != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(principal.getName());
            perfil.getCitas().add(hora + " - " + usuario.getUsername());
            perfilService.guardarPerfil(perfil, perfil.getUsuario().getId());
        }
        return "redirect:/perfil/ver/" + perfilId;
    }

}
