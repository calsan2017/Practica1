package Laboral.modelo;

import Laboral.excepciones.DatosNoCorrectosException;

public class Persona {

    private String nombre;
    private String dni;
    private char sexo;

    public Persona(String nombre, String dni, char sexo) {
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosNoCorrectosException("Tienes que poner un nombre");
        }

        if (sexo == 'm' || sexo == 'f' || sexo == 'M' || sexo == 'F') {
            this.sexo = Character.toLowerCase(sexo);
        } else {
            throw new DatosNoCorrectosException("El sexo debe ser m o f");
        }

        this.nombre = nombre;
        this.dni = dni;
    }

    public Persona(String nombre, char sexo) {
        this(nombre, "", sexo);
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void Imprimir() {
        System.out.println("Nombre: " + this.nombre + ", DNI: " + this.dni);
    }
}
