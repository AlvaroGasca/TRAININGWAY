/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.noticia;

import com.app.usuario.UsuarioService;
import com.app.usuario.Usuario;
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
public class NoticiaService {

    @Autowired
    private NoticiasRepository noticiaRepository;
    @Autowired
    private UsuarioService usuarioService;

    public List<Noticia> obtenerNoticiasPorUsuario(Usuario usuario) {
        return noticiaRepository.findByUsuario(usuario);
    }

    @Transactional
    public void guardarNoticia(Noticia noticia, Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        noticia.setUsuario(usuario);
        noticia.setFecha(LocalDateTime.now());
        noticia.setMeGusta(0);
        noticia.setNoMeGusta(0);
        noticiaRepository.save(noticia);
    }

    public Noticia obtenerNoticiaPorId(Long id) {
        return noticiaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }

    public List<Noticia> obtenerTodasLasNoticiasOrdenadasPorFecha() {
        return noticiaRepository.findAllOrderByFechaDesc();
    }

    @Transactional
    public void incrementarMeGusta(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        if (noticia != null) {
            noticia.setMeGusta(noticia.getMeGusta() + 1);
            noticiaRepository.save(noticia);
        }
    }

    @Transactional
    public void incrementarNoMeGusta(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        if (noticia != null) {
            noticia.setNoMeGusta(noticia.getNoMeGusta() + 1);
            noticiaRepository.save(noticia);
        }
    }

    @Transactional
    public void decrementarMeGusta(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        if (noticia != null && noticia.getMeGusta() > 0) {
            noticia.setMeGusta(noticia.getMeGusta() - 1);
            noticiaRepository.save(noticia);
        }
    }

    @Transactional
    public void decrementarNoMeGusta(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        if (noticia != null && noticia.getNoMeGusta() > 0) {
            noticia.setNoMeGusta(noticia.getNoMeGusta() - 1);
            noticiaRepository.save(noticia);
        }
    }

}
