/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.usuariosCoches;

import com.usuariosCoches.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alvarogasca
 */
public interface CochesRepository extends JpaRepository<Coche, Long> {
    List<Coche> findByUsuario(Usuario usuario);

}
