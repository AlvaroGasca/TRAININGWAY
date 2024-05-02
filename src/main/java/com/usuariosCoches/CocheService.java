/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usuariosCoches;

import com.usuariosCoches.usuario.UsuarioService;
import com.usuariosCoches.usuario.Usuario;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvarogasca
 */
@Service
public class CocheService {

    @Autowired
    private CochesRepository cocheRepository;
    @Autowired
    private UsuarioService usuarioService; 

    public List<Coche> obtenerCochesPorUsuario(Usuario usuario) {
        return cocheRepository.findByUsuario(usuario);
    }

    
    @Transactional
    public void guardarCoche(Coche coche, Long idUsuario) {
    Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
    coche.setUsuario(usuario);
    cocheRepository.save(coche);
}
    
    public Coche obtenerCochePorId(Long id) {
        return cocheRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarCoche(Long id) {
        cocheRepository.deleteById(id);
    }

}
