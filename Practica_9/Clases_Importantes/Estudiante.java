package Practica_9.Clases_Importantes;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Estudiante extends Persona implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "secuencia1en1")
    @SequenceGenerator(allocationSize = 1, name = "secuencia1en1")
    private Integer id;
    private int participacion;

    private LocalDateTime fechaParticipacion;

    public LocalDateTime getfechaParticipacion() {
        return fechaParticipacion;
    }

    public void setfechaParticipacion(LocalDateTime fechaParticipacion) {
        this.fechaParticipacion = fechaParticipacion;
    }

    public Estudiante(String nombre, String apellidos, int participacion){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.participacion = participacion;
    }

    public Estudiante(String nombre, int participacion){
        this.nombre = nombre;
        this.participacion = participacion;
    }

    public Estudiante(String nombre, int participacion, LocalDateTime fechaParticipacion){
        this.nombre = nombre;
        this.participacion = participacion;
        this.fechaParticipacion = fechaParticipacion;
    }

    public Estudiante() {

    }

    public int getParticipacion() {
        return participacion;
    }

    public void setParticipacion(int participacion) {
        this.participacion = participacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Nombre: " + getNombre() + " | Participación: " + getParticipacion() + " | Fecha Participación: " + getfechaParticipacion();
    }
}
