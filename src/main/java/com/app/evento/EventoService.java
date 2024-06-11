/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.evento;

import com.app.contador.Contador;
import com.app.contador.ContadorRepository;
import com.app.like.MeGusta;
import com.app.like.MeGustaRepository;
import com.app.usuario.Usuario;
import com.app.usuario.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvarogasca
 */
@Service
public class EventoService {
    @Autowired
    private EventosRepository eventoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MeGustaRepository likeRepository;
    @Autowired
    private ContadorRepository contadorRepository;

    public List<Evento> obtenerEventosPorUsuario(Usuario usuario) {
        return eventoRepository.findByUsuario(usuario);
    }

    @Transactional
    public void guardarEvento(Evento evento, Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        evento.setUsuario(usuario);
        evento.setFecha(LocalDateTime.now());
        evento.setMeGusta(0);
        eventoRepository.save(evento);
    }

    public Evento obtenerEventoPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public List<Evento> obtenerTodosLosEventosOrdenadosPorFecha() {
        return eventoRepository.findAllOrderByFechaDesc();
    }

    @Transactional
    public void incrementarMeGusta(Long idEvento, Long idUsuario) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (evento != null && usuario != null) {
            if (!likeRepository.existsByUsuarioAndEvento(usuario, evento)) {
                evento.setMeGusta(evento.getMeGusta() + 1);
                eventoRepository.save(evento);

                MeGusta like = new MeGusta();
                like.setUsuario(usuario);
                like.setEvento(evento);
                likeRepository.save(like);
            } else if (likeRepository.existsByUsuarioAndEvento(usuario, evento)) {
                evento.setMeGusta(evento.getMeGusta() - 1);
                eventoRepository.save(evento);
                likeRepository.deleteByUsuarioAndEvento(usuario, evento);
            }
        }
    }

    @Transactional
    public void decrementarMeGusta(Long idEvento, Long idUsuario) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (evento != null && usuario != null) {
            if (likeRepository.existsByUsuarioAndEvento(usuario, evento)) {
                evento.setMeGusta(evento.getMeGusta() - 1);
                eventoRepository.save(evento);
                likeRepository.deleteByUsuarioAndEvento(usuario, evento);
            }
        }
    }
    
    @Transactional
    public void incrementarContador(Long idEvento, Long idUsuario) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (evento != null && usuario != null) {
                evento.setContador(evento.getContador() + 1);
                eventoRepository.save(evento);

                Contador contador = new Contador();
                contador.setUsuario(usuario);
                contador.setEvento(evento);
                contadorRepository.save(contador);
            
        }
    }
}
