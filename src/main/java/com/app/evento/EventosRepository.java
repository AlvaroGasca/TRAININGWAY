/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.evento;

import com.app.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author alvarogasca
 */
public interface EventosRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByUsuario(Usuario usuario);
    
    @Query("SELECT n FROM Evento n ORDER BY n.fecha DESC")
    List<Evento> findAllOrderByFechaDesc();
    
}
