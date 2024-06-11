/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.perfil;

import com.app.usuario.Usuario;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alvarogasca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "perfiles")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(length = 300)
    private String descripcion;

    @Column(name = "mostrar_telefono")
    private boolean mostrarTelefono;

    @Column(name = "mostrar_correo")
    private boolean mostrarCorreo;

    @ElementCollection
    @CollectionTable(name = "resenas", joinColumns = @JoinColumn(name = "perfil_id"))
    @Column(name = "resena")
    private List<Integer> resenas;

    @Column(name = "disponibilidad")
    private String disponibilidad;
    
    @Column(name = "horario_manana_inicio")
    private LocalTime horarioMananaInicio;

    @Column(name = "horario_manana_fin")
    private LocalTime horarioMananaFin;

    @Column(name = "horario_tarde_inicio")
    private LocalTime horarioTardeInicio;

    @Column(name = "horario_tarde_fin")
    private LocalTime horarioTardeFin;

    @ElementCollection
    @CollectionTable(name = "citas", joinColumns = @JoinColumn(name = "perfil_id"))
    @Column(name = "cita")
    private List<String> citas;
    
    @ElementCollection
    @CollectionTable(name = "dias_disponibles", joinColumns = @JoinColumn(name = "perfil_id"))
    @Column(name = "dia_disponible")
    private List<String> diasDisponibles;
    
}
