/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Laboral.conexion;

import java.sql.*;



/**
 *
 * @author Usuario25
 */
public class conexion {
    
     private static final String URL      = "jdbc:mariadb://localhost:3306/gestion_nominas";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "123456";
    
  //  private static  String;
    private static Connection conexion = null;

    /**
     * Devuelve la conexión activa. Si no existe, la crea.
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida con la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            System.err.println("  Comprueba que MySQL está arrancado y los datos son correctos.");
        }
        return conexion;
    }
    
    /**
     * Cierra la conexión activa.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
}
