/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.noticia;
import com.app.usuario.Especialidad;
import com.app.usuario.Usuario;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence. Column; 
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence. GeneratedValue; 
import javax.persistence. GenerationType;
import javax.persistence. Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok. Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author alvarogasca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "noticias")
public class Noticia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING) // Especifica el tipo de enumeración
    @Column(name = "especialidad")
    private Especialidad especialidad;
    
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd'-'HH:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "cuerpo")
    private String cuerpo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "me_gusta")
    private Integer meGusta;
        
}
