/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usuariosCoches.seguridad;

import com.usuariosCoches.usuario.Usuario;
import com.usuariosCoches.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/convertirEntrenador")
public class EntrenadorController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/{id}")
    public String mostrarFormularioConvertirEntrenador(@PathVariable Long id, Model model) {
    Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
    if (usuario != null) {
        model.addAttribute("usuario", usuario);
        return "convertirEntrenador";
    } else {
        return "redirect:/coches/mis-coches";
    }
}
    @PostMapping("/{id}")
public String convertirEntrenador(@PathVariable Long id, @ModelAttribute("usuario") Usuario usuario) {
    // Obtener el usuario existente por su ID
    Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
    if (usuarioExistente != null) {
        // Actualizar la especialidad del usuario existente con la especialidad del formulario
        usuarioExistente.setEspecialidad(usuario.getEspecialidad());

        // Guardar el usuario actualizado en la base de datos
        usuarioService.guardarUsuario(usuarioExistente);
    }
    return "redirect:/coches/mis-coches";
}


}
