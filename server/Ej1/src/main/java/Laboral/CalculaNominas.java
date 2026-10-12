package Laboral;

public class CalculaNominas {

    public static void main(String[] args) throws DatosNoCorrectosException {

        try {

            Empleado empleado = new Empleado("James Cosling", "32000032G", 'M', 4, 7);
            Empleado empleado1 = new Empleado("Ada Lovelace", "32000031R", 'F');
            Nomina no = new Nomina();

            int opcion;

            do {
                System.out.println("MENÚ");
                System.out.println("1. Mostrar empleados y nóminas");
                System.out.println("2. Cambiar categoría de James");
                System.out.println("3. Aumentar años de Ada");
                System.out.println("4. Mostrar nóminas después de modificar");
                System.out.println("5. Salir");
                System.out.print("Elige una opción: ");

                java.util.Scanner sc = new java.util.Scanner(System.in);
                opcion = sc.nextInt();

                switch (opcion) {

                    case 1:
                        escribe(empleado, empleado1, no);
                        break;

                    case 2:
                        System.out.print("Introduce la nueva categoría para James: ");
                        int categoria = sc.nextInt();
                        empleado.setCategoria(categoria);
                        System.out.println("Categoría modificada correctamente.");
                        break;

                    case 3:
                        empleado1.incrAnyos();
                        System.out.println("Años de Ada incrementados correctamente.");
                        break;

                    case 4:
                        System.out.println("\nDespués de las modificaciones:");
                        escribe(empleado, empleado1, no);
                        break;

                    case 5:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } while (opcion != 5);

        } catch (DatosNoCorrectosException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void escribe (Empleado empleado, Empleado empleado1, Nomina no){

        empleado.Imprimir();
        System.out.println("Nomina empleado 1: " + no.sueldo(empleado));
        empleado1.Imprimir();
        System.out.println("Nomina empleado 2: " + no.sueldo(empleado1));

    }
}
