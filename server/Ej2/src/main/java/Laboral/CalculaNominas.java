package Laboral;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;

public class CalculaNominas {

    public static void main(String args[])throws DatosNoCorrectosException {

        try{

            conexion con = new conexion();
            Empleado empleado = new Empleado("James Cosling","32000032G",'M', 4,7);
            Empleado empleado1 = new Empleado("Ada Lovelace","32000031R",'F');
            Nomina no = new Nomina();
            CalculaNominas calc = new CalculaNominas();

            escribe(empleado, empleado1, no);
            empleado.setCategoria(9);
            empleado1.incrAnyos();

            System.out.println("Despues de modificacion: ");
            escribe(empleado, empleado1, no);
            calc.readText("empleados.txt");
            conexion.getConexion();

        }catch (DatosNoCorrectosException e){
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

    private static void escribe (Empleado empleado, Empleado empleado1, Nomina no){

        empleado.Imprimir();
        System.out.println("Nomina empleado 1: " + no.sueldo(empleado));
        empleado1.Imprimir();
        System.out.println("Nomina empleado 2: " + no.sueldo(empleado1));

    }
}
