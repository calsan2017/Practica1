package Laboral;

import java.sql.*;

public class ConexionMariaDB {

    public static void main(String args[]) {
        final String USER = "company";
        final String PASS = "123456";
        final String DB_NAME = "company_db";
        final String CONN_URL = "jdbc:mariadb://localhost:3306/gestion_nominas";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(CONN_URL, USER, PASS);
            System.out.println("Conexión válida: " + conn.isValid(10));
            System.out.println("Estado del autocommit: " + conn.getAutoCommit());
        } catch (SQLException e) {
            System.out.println("Ocurrió una excepción al conectar a la BD");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }
        }
    }
}
