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

/** Busca los empleados y envía los resultados a un JSP. */
@WebServlet("/empleados")
public class MostrarEmpleadosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT id, nombre, dni, sexo, categoria, anyos FROM empleados ORDER BY nombre";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.util.List<EmpleadoWeb> lista = new java.util.ArrayList<>();
            while (rs.next()) {
                lista.add(new EmpleadoWeb(
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("sexo"), rs.getInt("categoria"), rs.getInt("anyos")));
            }
            request.setAttribute("empleados", lista);
            request.getRequestDispatcher("/WEB-INF/empleados.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensajeError", "No se han podido consultar los empleados. Comprueba la conexión y las tablas.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
