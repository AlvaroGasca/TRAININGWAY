/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
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
            List<String> horarios = generarHorariosDisponibles(perfil);
            model.addAttribute("horarios", horarios);
            List<String> diasDisponibles = perfil.getDiasDisponibles();
            if (diasDisponibles == null) {
                diasDisponibles = new ArrayList<>();
            }

            // Convertir diasDisponibles a JSON
            ObjectMapper mapper = new ObjectMapper();
            try {
                String diasDisponiblesJson = mapper.writeValueAsString(diasDisponibles);
                model.addAttribute("diasDisponiblesJson", diasDisponiblesJson);
                // Depuración: Verifica el contenido del JSON
                System.out.println("Días Disponibles JSON: " + diasDisponiblesJson);
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAttribute("diasDisponibles", diasDisponibles);
            return "formularioCita";
        } else {
            return "redirect:/perfil/todos";
        }
    }

    @PostMapping("/agendar")
    public String agendarCita(@RequestParam("perfilId") Long perfilId, @RequestParam("hora") String hora, @RequestParam("dia") String dia, Principal principal) {
        Perfil perfil = perfilService.obtenerPerfilPorId(perfilId);
        if (perfil != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(principal.getName());
            String cita = dia + " " + hora + " - " + usuario.getUsername();
            if (!perfil.getCitas().contains(cita)) {
                perfil.getCitas().add(cita);
                perfilService.guardarPerfil(perfil, perfil.getUsuario().getId());
            }
        }
        return "redirect:/perfil/ver/" + perfilId;
    }

    private List<String> generarHorariosDisponibles(Perfil perfil) {
        List<String> horariosDisponibles = new ArrayList<>();
        
        LocalTime inicioManana = perfil.getHorarioMananaInicio();
        LocalTime finManana = perfil.getHorarioMananaFin();
        LocalTime inicioTarde = perfil.getHorarioTardeInicio();
        LocalTime finTarde = perfil.getHorarioTardeFin();

        if (inicioManana != null && finManana != null) {
            while (inicioManana.isBefore(finManana)) {
                horariosDisponibles.add(inicioManana.toString());
                inicioManana = inicioManana.plusMinutes(30);
            }
        }

        if (inicioTarde != null && finTarde != null) {
            while (inicioTarde.isBefore(finTarde)) {
                horariosDisponibles.add(inicioTarde.toString());
                inicioTarde = inicioTarde.plusMinutes(30);
            }
        }

        return horariosDisponibles;
    }
    
    @GetMapping("/mis-citas")
    public String verCitasUsuario(Model model, Principal principal) {
    String username = principal.getName();
    Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
    Perfil perfil = perfilService.obtenerPerfilPorUsuario(usuario);
    if (perfil == null) {
        return "redirect:/perfil/formularioPerfil";
    }

    List<String> citas = new ArrayList<>(perfil.getCitas());

    // Agrega las citas en las que el usuario es el cliente
    List<Perfil> perfiles = perfilService.obtenerTodosLosPerfiles();
    for (Perfil p : perfiles) {
        for (String cita : p.getCitas()) {
            if (cita.contains(username)) {
                citas.add(cita);
            }
        }
    }

    model.addAttribute("citas", citas);
    return "misCitas";
}

    // Nuevo método para cancelar una cita
    @PostMapping("/cancelar")
    public String cancelarCita(@RequestParam("cita") String cita, Principal principal) {
    String username = principal.getName();
    Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);

    // Primero, intentamos encontrar la cita en el perfil del usuario actual
    Perfil perfil = perfilService.obtenerPerfilPorUsuario(usuario);
    if (perfil != null && perfil.getCitas().contains(cita)) {
        perfil.getCitas().remove(cita);
        perfilService.guardarPerfil(perfil, perfil.getUsuario().getId());
    } else {
        // Si no es el propietario, buscamos entre todos los perfiles
        List<Perfil> perfiles = perfilService.obtenerTodosLosPerfiles();
        for (Perfil p : perfiles) {
            if (p.getCitas().contains(cita)) {
                p.getCitas().remove(cita);
                perfilService.guardarPerfil(p, p.getUsuario().getId());
                break;
            }
        }
    }

    return "redirect:/cita/mis-citas";
}

}
