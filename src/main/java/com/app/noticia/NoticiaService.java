/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.noticia;

import com.app.like.MeGusta;
import com.app.usuario.UsuarioService;
import com.app.usuario.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.like.MeGustaRepository;

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
    @Autowired
    private MeGustaRepository likeRepository;

    public List<Noticia> obtenerNoticiasPorUsuario(Usuario usuario) {
        return noticiaRepository.findByUsuario(usuario);
    }

    @Transactional
    public void guardarNoticia(Noticia noticia, Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        noticia.setUsuario(usuario);
        noticia.setFecha(LocalDateTime.now());
        noticia.setMeGusta(0);
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
    public void incrementarMeGusta(Long idNoticia, Long idUsuario) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (noticia != null && usuario != null) {
            if (!likeRepository.existsByUsuarioAndNoticia(usuario, noticia)) {
                noticia.setMeGusta(noticia.getMeGusta() + 1);
                noticiaRepository.save(noticia);

                MeGusta like = new MeGusta();
                like.setUsuario(usuario);
                like.setNoticia(noticia);
                likeRepository.save(like);
            } else if (likeRepository.existsByUsuarioAndNoticia(usuario, noticia)){
                noticia.setMeGusta(noticia.getMeGusta() - 1);
                noticiaRepository.save(noticia);
                likeRepository.deleteByUsuarioAndNoticia(usuario, noticia);
            }
        }
    }

    @Transactional
    public void decrementarMeGusta(Long idNoticia, Long idUsuario) {
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (noticia != null && usuario != null) {
            if (likeRepository.existsByUsuarioAndNoticia(usuario, noticia)) {
                noticia.setMeGusta(noticia.getMeGusta() - 1);
                noticiaRepository.save(noticia);
                likeRepository.deleteByUsuarioAndNoticia(usuario, noticia);
            }
        }
    }
    
    public List<Noticia> obtenerUltimasNoticias() {
        return noticiaRepository.findTop4ByOrderByFechaDesc();
    }


}
