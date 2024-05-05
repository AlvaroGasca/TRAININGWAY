/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usuariosCoches.seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author alvarogasca
 */
@Controller
public class TrainningWayController {

    @GetMapping("/trainningway")
    public String mostrarTrainningWay() {
        return "trainningway"; // Nombre del archivo HTML sin la extensión
    }
}