/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.like;

import com.app.noticia.Noticia;
import com.app.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alvarogasca
 */
public interface MeGustaRepository extends JpaRepository<MeGusta, Long> {
    boolean existsByUsuarioAndNoticia(Usuario usuario, Noticia noticia);
    void deleteByUsuarioAndNoticia(Usuario usuario, Noticia noticia);
}
