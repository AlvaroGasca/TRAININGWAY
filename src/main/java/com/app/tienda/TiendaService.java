/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tienda;

import com.app.contador.Contador;
import com.app.contador.ContadorRepository;
import com.app.like.MeGusta;
import com.app.like.MeGustaRepository;
import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvarogasca
 */
@Service
public class TiendaService {
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MeGustaRepository likeRepository;
    @Autowired
    private ContadorRepository contadorRepository;

    public List<Tienda> obtenerProductosPorUsuario(Usuario usuario) {
        return tiendaRepository.findByUsuario(usuario);
    }

    @Transactional
    public void guardarProducto(Tienda tienda, Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        tienda.setUsuario(usuario);
        tienda.setFecha(LocalDateTime.now());
        tienda.setMeGusta(0);
        tiendaRepository.save(tienda);
    }

    public Tienda obtenerProductoPorId(Long id) {
        return tiendaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        tiendaRepository.deleteById(id);
    }

    public List<Tienda> obtenerTodosLosProductosOrdenadosPorFecha() {
        return tiendaRepository.findAllOrderByFechaDesc();
    }

    @Transactional
    public void incrementarMeGusta(Long idProducto, Long idUsuario) {
        Tienda tienda = tiendaRepository.findById(idProducto).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (tienda != null && usuario != null) {
            if (!likeRepository.existsByUsuarioAndTienda(usuario, tienda)) {
                tienda.setMeGusta(tienda.getMeGusta() + 1);
                tiendaRepository.save(tienda);

                MeGusta like = new MeGusta();
                like.setUsuario(usuario);
                like.setTienda(tienda);
                likeRepository.save(like);
            }
        }
    }

    @Transactional
    public void decrementarMeGusta(Long idTienda, Long idUsuario) {
        Tienda tienda = tiendaRepository.findById(idTienda).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (tienda != null && usuario != null) {
            if (likeRepository.existsByUsuarioAndTienda(usuario, tienda)) {
                tienda.setMeGusta(tienda.getMeGusta() - 1);
                tiendaRepository.save(tienda);
                likeRepository.deleteByUsuarioAndTienda(usuario, tienda);
            }
        }
    }
    
    @Transactional
    public void incrementarContador(Long idTienda, Long idUsuario) {
        Tienda tienda = tiendaRepository.findById(idTienda).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (tienda != null && usuario != null) {
                tienda.setContador(tienda.getContador() + 1);
                tiendaRepository.save(tienda);

                Contador contador = new Contador();
                contador.setUsuario(usuario);
                contador.setTienda(tienda);
                contadorRepository.save(contador);
            
        }
    }
}
