package Laboral;

public class Persona {

    private String nombre;
    private String dni;
    private char sexo;

    public Persona(String nombre, String dni, char sexo) {
        if(nombre.isEmpty()){
            throw new DatosNoCorrectosException("Error tienes que poner un nombre");
        }

        if(sexo=='M' ||sexo=='F'){
            this.sexo=sexo;
        }else{
            throw new DatosNoCorrectosException("Error tienes que poner F o M");
        }

        this.nombre = nombre;
        this.dni = dni;

    }

    public Persona(String nombre, char sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    public void Imprimir() {

        System.out.println("Nombre: " + this.nombre + ", DNI: " + this.dni);

    }
}
