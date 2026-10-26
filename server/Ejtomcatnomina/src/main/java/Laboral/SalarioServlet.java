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

/** Busca el sueldo de un empleado usando su DNI. */
@WebServlet("/salario")
public class SalarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni = request.getParameter("dni");
        if (dni == null || dni.trim().isEmpty()) {
            request.setAttribute("mensaje", "Escribe un DNI para realizar la búsqueda.");
            request.getRequestDispatcher("/resultadoSalario.jsp").forward(request, response);
            return;
        }

        String sql = "SELECT e.nombre, e.dni, n.sueldo, n.fecha_calculo " +
                     "FROM empleados e JOIN nominas n ON e.id = n.empleado_id WHERE e.dni = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("nombre", rs.getString("nombre"));
                    request.setAttribute("dni", rs.getString("dni"));
                    request.setAttribute("sueldo", rs.getString("sueldo"));
                    request.setAttribute("fecha", rs.getString("fecha_calculo"));
                } else {
                    request.setAttribute("mensaje", "No se ha encontrado una nómina para ese DNI.");
                }
            }
            request.getRequestDispatcher("/resultadoSalario.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensajeError", "No se ha podido consultar el salario. Comprueba que existan las tablas y columnas indicadas.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
