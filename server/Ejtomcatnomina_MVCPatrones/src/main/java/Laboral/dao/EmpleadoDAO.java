package Laboral.dao;

import Laboral.modelo.EmpleadoWeb;
import Laboral.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PATRON DAO: concentra todas las consultas SQL de empleados y nominas.
 * El controlador no necesita conocer las consultas SQL.
 */
public class EmpleadoDAO {

    public List<EmpleadoWeb> listar() throws SQLException {
        List<EmpleadoWeb> lista = new ArrayList<>();
        String sql = "SELECT id,nombre,dni,sexo,categoria,anyos FROM empleados ORDER BY nombre";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearEmpleado(rs));
        }
        return lista;
    }

    public List<EmpleadoWeb> buscar(String campo, String valor) throws SQLException {
        // Solo se permiten estas columnas para evitar introducir SQL desde el formulario.
        String columna;
        switch (campo == null ? "" : campo) {
            case "dni": columna = "dni"; break;
            case "sexo": columna = "sexo"; break;
            case "categoria": columna = "categoria"; break;
            case "anyos": columna = "anyos"; break;
            default: columna = "nombre";
        }
        // En el formulario se usa m/f, pero MariaDB almacena H/M.
        if ("sexo".equals(campo) && valor != null) {
            String sexoFormulario = valor.trim();
            if ("m".equalsIgnoreCase(sexoFormulario)) valor = "H";
            else if ("f".equalsIgnoreCase(sexoFormulario)) valor = "M";
        }

        List<EmpleadoWeb> lista = new ArrayList<>();
        String sql = "SELECT id,nombre,dni,sexo,categoria,anyos FROM empleados WHERE "
                + columna + " LIKE ? ORDER BY nombre";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (valor == null ? "" : valor.trim()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearEmpleado(rs));
            }
        }
        return lista;
    }

    public EmpleadoWeb obtener(int id) throws SQLException {
        String sql = "SELECT id,nombre,dni,sexo,categoria,anyos FROM empleados WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearEmpleado(rs);
            }
        }
        return null;
    }

    public void actualizar(EmpleadoWeb e, int sueldo) throws SQLException {
        // El nombre y el DNI no se modifican. Solo se actualizan estos tres campos.
        String sqlEmpleado = "UPDATE empleados SET sexo=?, categoria=?, anyos=? WHERE id=?";
        String sqlNomina = "UPDATE nominas SET sueldo=?, fecha_calculo=CURRENT_DATE WHERE empleado_id=?";
        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                    // Conversión del formulario (m/f) al formato de la BD (H/M).
                    ps.setString(1, convertirSexoABaseDatos(e.getSexo()));
                    ps.setInt(2, e.getCategoria());
                    ps.setInt(3, e.getAnyos());
                    ps.setInt(4, e.getId());
                    if (ps.executeUpdate() == 0) throw new SQLException("No existe ese empleado.");
                }
                int filas;
                try (PreparedStatement ps = con.prepareStatement(sqlNomina)) {
                    ps.setInt(1, sueldo);
                    ps.setInt(2, e.getId());
                    filas = ps.executeUpdate();
                }
                if (filas == 0) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO nominas (empleado_id,sueldo,fecha_calculo) VALUES (?,?,CURRENT_DATE)")) {
                        ps.setInt(1, e.getId());
                        ps.setInt(2, sueldo);
                        ps.executeUpdate();
                    }
                }
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public NominaDatos buscarNomina(String dni) throws SQLException {
        String sql = "SELECT e.nombre,e.dni,n.sueldo,n.fecha_calculo FROM empleados e "
                + "JOIN nominas n ON e.id=n.empleado_id WHERE e.dni=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new NominaDatos(rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("sueldo"), rs.getString("fecha_calculo"));
            }
        }
        return null;
    }

    private EmpleadoWeb mapearEmpleado(ResultSet rs) throws SQLException {
        return new EmpleadoWeb(rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"),
                convertirSexoAFormulario(rs.getString("sexo")),
                rs.getInt("categoria"), rs.getInt("anyos"));
    }

    // La aplicación utiliza m/f; la base de datos utiliza H/M.
    private String convertirSexoABaseDatos(String sexo) {
        if (sexo == null) return null;
        if ("m".equalsIgnoreCase(sexo.trim())) return "H";
        if ("f".equalsIgnoreCase(sexo.trim())) return "M";
        throw new IllegalArgumentException("El sexo debe ser m o f.");
    }

    private String convertirSexoAFormulario(String sexoBD) {
        if (sexoBD == null) return "";
        if ("H".equalsIgnoreCase(sexoBD.trim())) return "m";
        if ("M".equalsIgnoreCase(sexoBD.trim())) return "f";
        return sexoBD;
    }

    public static class NominaDatos {
        private final String nombre, dni, sueldo, fecha;
        public NominaDatos(String nombre, String dni, String sueldo, String fecha) {
            this.nombre=nombre; this.dni=dni; this.sueldo=sueldo; this.fecha=fecha;
        }
        public String getNombre(){return nombre;}
        public String getDni(){return dni;}
        public String getSueldo(){return sueldo;}
        public String getFecha(){return fecha;}
    }
}
