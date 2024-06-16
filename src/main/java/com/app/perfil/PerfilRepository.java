/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author alvarogasca
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByUsuario(Usuario usuario);
    
@Query("SELECT p FROM Perfil p WHERE p.usuario.especialidad IS NOT NULL")    
    List<Perfil> findAll();
    
    @Query("SELECT p FROM Perfil p WHERE p.usuario.ubicacion LIKE %:ubicacion%")
    List<Perfil> findByUbicacion(@Param("ubicacion") String ubicacion);

}
