package Laboral;

public class Nomina {

    private static final int SUELDO_BASE[] = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};

    /**
     *
     * @param empleado
     * @return sueldo
     */
    public int sueldo(Empleado empleado) {

        int sueldo = SUELDO_BASE[empleado.getCategoria()] + 5000 * empleado.anyos;
        return sueldo;
    }
}
