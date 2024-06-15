/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.favoritos;

import com.app.perfil.Perfil;
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
public class FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;
    @Autowired
    private UsuarioService usuarioService;

    public List<Favorito> obtenerFavoritosPorUsuario(String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        return favoritoRepository.findByUsuario(usuario);
    }

    public void agregarAFavoritos(Long perfilId, String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        Perfil perfil = new Perfil();
        perfil.setId(perfilId);

        // Comprobar si el perfil ya está en los favoritos del usuario
        if (favoritoRepository.findByUsuarioAndPerfil(usuario, perfil) == null) {
            Favorito favorito = new Favorito(null, usuario, perfil);
            favoritoRepository.save(favorito);
        }
    }

    public void eliminarDeFavoritos(Long perfilId, String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(nombreUsuario);
        Favorito favorito = favoritoRepository.findByUsuario(usuario)
                .stream()
                .filter(fav -> fav.getPerfil().getId().equals(perfilId))
                .findFirst()
                .orElse(null);
        if (favorito != null) {
            favoritoRepository.delete(favorito);
        }
    }
}
