package Laboral;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CalculaNominas {
    public static void main(String[] args) {
        try {
            Empleado empleado = new Empleado("James Cosling", "32000032G", 'H', 4, 7);
            Empleado empleado2 = new Empleado("Ada Lovelace", "32000031R", 'M', 6, 1);
            Nomina nomina = new Nomina();
            empleado.Imprimir();
            System.out.println("Nómina: " + nomina.sueldo(empleado));
            empleado2.Imprimir();
            System.out.println("Nómina: " + nomina.sueldo(empleado2));
            leerArchivo("empleados.txt");
            try (Connection con = conexion.conectar()) {
                System.out.println("Conexión con MariaDB correcta");
            }
        } catch (DatosNoCorrectosException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    public static void leerArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) System.out.println(linea);
        } catch (IOException e) {
            System.out.println("No se ha podido leer el archivo: " + archivo);
        }
    }
}
