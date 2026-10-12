package Laboral;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;

public class CalculaNominas {

    public static void main(String args[]) throws DatosNoCorrectosException {
        try {
            conexion con = new conexion();
            Empleado empleado = new Empleado("James Cosling", "32000032G", 'M', 4, 7);
            Empleado empleado1 = new Empleado("Ada Lovelace", "32000031R", 'F');
            Nomina no = new Nomina();
            CalculaNominas calc = new CalculaNominas();
            Scanner sc = new Scanner(System.in);
            int opcion;
            do {
                System.out.println("MENU");
                System.out.println("1. Mostrar empleados");
                System.out.println("2. Mostrar nóminas");
                System.out.println("3. Modificar categoría de James");
                System.out.println("4. Incrementar años de Ada");
                System.out.println("5. Leer empleados.txt");
                System.out.println("6. Actualizar empleados.txt");
                System.out.println("7. Mostrar empleados de la BD");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("EMPLEADOS");
                        empleado.Imprimir();
                        empleado1.Imprimir();
                        break;
                    case 2:
                        System.out.println("NÓMINAS");
                        System.out.println("Nómina empleado 1: " + no.sueldo(empleado));
                        System.out.println("Nómina empleado 2: " + no.sueldo(empleado1));
                        break;
                    case 3:
                        System.out.print("Introduce la nueva categoría de James: ");
                        int categoria = sc.nextInt();
                        empleado.setCategoria(categoria);
                        System.out.println("Categoría modificada correctamente.");
                        break;
                    case 4:
                        empleado1.incrAnyos();
                        System.out.println("Años de Ada incrementados correctamente.");
                        break;
                    case 5:
                        System.out.println("CONTENIDO DEL TXT");
                        calc.readText("empleados.txt");
                        break;
                    case 6:
                        actualizarTxt();
                        break;
                    case 7:
                        mostrarEmpleadosBD();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Inténtalo de nuevo.");
                }
            } while (opcion != 0);
            sc.close();
        } catch (DatosNoCorrectosException e) {
            System.out.println(e.getMessage());
        }
    }


    public void readText(String te) {

        try (BufferedReader br = new BufferedReader(new FileReader(te))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + te);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }

    private static void escribe(Empleado empleado, Empleado empleado1, Nomina no) {

        empleado.Imprimir();
        System.out.println("Nomina empleado 1: " + no.sueldo(empleado));
        empleado1.Imprimir();
        System.out.println("Nomina empleado 2: " + no.sueldo(empleado1));

    }

    public static void mostrarEmpleadosBD() {

        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM empleados";

        try (Statement st = conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Empleados en la base de datos:");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String sexo = rs.getString("sexo");
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");

                System.out.println( "Nombre: "+ nombre + " DNI: " + dni + " Sexo: " + sexo + "Categoria: "+ categoria + " Años: " + anyos);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los empleados: " + e.getMessage());
        }
    }

    public static void actualizarTxt() {

        try {

            FileWriter fw = new FileWriter("empleados.txt");
            fw.write("pedro");
            fw.write(" luis");
            fw.close();
            System.out.println("Actualizado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
