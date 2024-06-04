/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.contador;

import com.app.evento.Evento;
import com.app.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alvarogasca
 */
public interface ContadorRepository extends JpaRepository<Contador, Long>{
    boolean existsByUsuarioAndEvento(Usuario usuario,Evento evento);
    void deleteByUsuarioAndEvento(Usuario usuario, Evento evento);
}
