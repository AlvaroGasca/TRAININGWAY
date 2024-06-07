/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvarogasca
 */
@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioService usuarioService;

    public void guardarPerfil(Perfil perfil, Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        perfil.setUsuario(usuario);
        perfilRepository.save(perfil);
    }

    public Perfil obtenerPerfilPorUsuario(Usuario usuario) {
        return perfilRepository.findByUsuario(usuario);
    }
    
    public Perfil obtenerPerfilPorId(Long id) {
    return perfilRepository.findById(id).orElse(null);
}
    
//    public List<Perfil> filtrarPerfiles(Especialidad especialidad, String ubicacion) {
//    if (especialidad != null && ubicacion != null) {
//        return perfilRepository.findByEspecialidadAndUbicacion(especialidad, ubicacion);
//    } else if (especialidad != null) {
//        return perfilRepository.findByEspecialidad(especialidad);
//    } else if (ubicacion != null) {
//        return perfilRepository.findByUbicacion(ubicacion);
//    } else {
//        return obtenerTodosLosPerfiles();
//    }
//}
    
    public List<Perfil> obtenerTodosLosPerfiles() {
        return perfilRepository.findAll();
    }


}
