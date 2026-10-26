package Laboral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static final String URL =
            "jdbc:mariadb://localhost:3306/gestion_nominas";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            return DriverManager.getConnection(
                    URL, USUARIO, PASSWORD
            );

        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encuentra el driver de MariaDB. Revisa las dependencias de Maven.", e
            );
        }
    }
}
