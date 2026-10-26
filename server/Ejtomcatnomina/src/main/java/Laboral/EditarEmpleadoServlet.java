package Laboral;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Muestra el formulario y guarda los cambios de un empleado. */
@WebServlet("/editarEmpleado")
public class EditarEmpleadoServlet extends HttpServlet {

    // Sueldos base de las categorías 1 a 10, siguiendo la clase Nomina del ejercicio.
    private static final int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String textoId = request.getParameter("id");
        try {
            int id = Integer.parseInt(textoId);
            String sql = "SELECT id, nombre, dni, sexo, categoria, anyos FROM empleados WHERE id = ?";
            try (Connection con = ConexionBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        EmpleadoWeb empleado = new EmpleadoWeb(
                                rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"),
                                rs.getString("sexo"), rs.getInt("categoria"), rs.getInt("anyos"));
                        request.setAttribute("empleado", empleado);
                        request.getRequestDispatcher("/editarEmpleado.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe ese empleado.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El identificador no es válido.");
        } catch (SQLException e) {
            throw new ServletException("No se ha podido cargar el empleado.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre").trim();
            String dni = request.getParameter("dni").trim();
            String sexo = request.getParameter("sexo").trim().toUpperCase();
            int categoria = Integer.parseInt(request.getParameter("categoria"));
            int anyos = Integer.parseInt(request.getParameter("anyos"));

            if (nombre.isEmpty() || dni.isEmpty() || !(sexo.equals("H") || sexo.equals("M"))
                    || categoria < 1 || categoria > 10 || anyos < 0) {
                request.setAttribute("mensajeError", "Revisa los datos: categoría de 1 a 10, años igual o mayores que 0 y sexo H o M.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // El sueldo se calcula a partir de la categoría y los años; no se recibe del formulario.
            int sueldo = SUELDO_BASE[categoria - 1] + 5000 * anyos;

            try (Connection con = ConexionBD.conectar()) {
                con.setAutoCommit(false);
                try {
                    String sqlEmpleado = "UPDATE empleados SET nombre = ?, dni = ?, sexo = ?, categoria = ?, anyos = ? WHERE id = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                        ps.setString(1, nombre);
                        ps.setString(2, dni);
                        ps.setString(3, sexo);
                        ps.setInt(4, categoria);
                        ps.setInt(5, anyos);
                        ps.setInt(6, id);
                        int filas = ps.executeUpdate();
                        if (filas == 0) {
                            throw new SQLException("No existe el empleado que intentas modificar.");
                        }
                    }

                    // Actualiza la nómina existente; si aún no tiene una, la crea.
                    int filasNomina;
                    String sqlNomina = "UPDATE nominas SET sueldo = ?, fecha_calculo = CURRENT_DATE WHERE empleado_id = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlNomina)) {
                        ps.setInt(1, sueldo);
                        ps.setInt(2, id);
                        filasNomina = ps.executeUpdate();
                    }
                    if (filasNomina == 0) {
                        String insertar = "INSERT INTO nominas (empleado_id, sueldo, fecha_calculo) VALUES (?, ?, CURRENT_DATE)";
                        try (PreparedStatement ps = con.prepareStatement(insertar)) {
                            ps.setInt(1, id);
                            ps.setInt(2, sueldo);
                            ps.executeUpdate();
                        }
                    }

                    con.commit();
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    con.setAutoCommit(true);
                }
            }

            response.sendRedirect(request.getContextPath() + "/buscarEmpleados");
        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError", "La categoría, los años y el identificador deben ser números.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "No se han podido guardar los cambios. Comprueba que el DNI no esté repetido y que exista la tabla nominas.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
