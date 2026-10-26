package Laboral;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conectar() throws SQLException {
        return conexion.conectar();
    }
}
