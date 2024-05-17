/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.noticia;

import com.app.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author alvarogasca
 */
public interface NoticiasRepository extends JpaRepository<Noticia, Long> {
    List<Noticia> findByUsuario(Usuario usuario);
    
    @Query("SELECT n FROM Noticia n ORDER BY n.fecha DESC")
    List<Noticia> findAllOrderByFechaDesc();

}
