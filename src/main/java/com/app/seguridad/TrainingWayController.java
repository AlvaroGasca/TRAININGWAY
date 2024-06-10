/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.seguridad;

import com.app.noticia.Noticia;
import com.app.noticia.NoticiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author alvarogasca
 */
@Controller
public class TrainingWayController {
    
    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/trainingway")
    public String mostrarTrainningWay(Model model) {
        List<Noticia> ultimasNoticias = noticiaService.obtenerUltimasNoticias();
        model.addAttribute("noticias", ultimasNoticias);
        return "trainingway";
    }
}