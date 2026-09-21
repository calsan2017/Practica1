package Laboral;

public class Empleado extends Persona {

    private int categoria;
    public int anyos;

    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) {
        super(nombre, dni, sexo);
        this.categoria = categoria;
        this.anyos = anyos;

        if (categoria >= 1 && categoria <= 10) {

            this.categoria = categoria;

        } else {

            throw new DatosNoCorrectosException("La categoria debe estar entre 1 e 10");

        }

        if (anyos > 0) {
            this.anyos = anyos;
        } else {
            throw new DatosNoCorrectosException("El anyos debe ser mayor o igual a 0");
        }
    }

    public Empleado(String nombre, String dni, char sexo) {
        super(nombre, dni, sexo);
    }

    /**
     *
     * @param anyos
     * @throws DatosNoCorrectosException
     */
    public void setAnyos(int anyos)throws DatosNoCorrectosException{

        if (anyos > 0) {
            this.anyos = anyos;
        } else {
            throw new DatosNoCorrectosException("El anyos debe ser mayor o igual a 0");
        }

    }

    /**
     *
     * @param categoria
     */
    public void setCategoria(int categoria) {

        if (categoria >= 1 && categoria <= 10) {

            this.categoria = categoria;

        } else {

            throw new DatosNoCorrectosException("El categoria no es valido");

        }

    }

    /**
     *
     * @return categoria
     */
    public int getCategoria() {
        return categoria;
    }

    public void incrAnyos() {
        this.anyos++;
    }

    public void imprime(){
        super.Imprimir();
        System.out.println("categoria: " + this.categoria + " anyos: " + this.anyos);
    }
}

