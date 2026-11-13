package Laboral.modelo;

/**
 * Calcula el sueldo de un empleado.
 */
public class Nomina {

    private static final int[] SUELDO_BASE = {
        50000, 70000, 90000, 110000, 130000,
        150000, 170000, 190000, 210000, 230000
    };

    public int sueldo(EmpleadoWeb empleado) {
        int categoria = empleado.getCategoria();
        int anyos = empleado.getAnyos();

        if (categoria < 1 || categoria > SUELDO_BASE.length) {
            throw new IllegalArgumentException("La categoría debe estar entre 1 y 10");
        }

        return SUELDO_BASE[categoria - 1] + 5000 * anyos;
    }
}
