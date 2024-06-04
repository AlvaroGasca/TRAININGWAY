/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tienda;

import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author alvarogasca
 */
public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    List<Tienda> findByUsuario(Usuario usuario);
    
    @Query("SELECT n FROM Tienda n ORDER BY n.fecha DESC")
    List<Tienda> findAllOrderByFechaDesc();
}
