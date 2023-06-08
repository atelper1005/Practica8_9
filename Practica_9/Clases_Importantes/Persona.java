package Practica_9.Clases_Importantes;

import javax.persistence.Transient;

public class Persona {
    public String nombre;
    @Transient
    public String apellidos;
    @Transient
    public int edad;


    public Persona(String nombre, String apellidos,  int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public Persona(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }


    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public Persona(){};

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
