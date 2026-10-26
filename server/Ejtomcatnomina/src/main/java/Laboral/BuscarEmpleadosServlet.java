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
import java.util.ArrayList;
import java.util.List;

/** Busca empleados por nombre, DNI, sexo, categoría o años trabajados. */
@WebServlet("/buscarEmpleados")
public class BuscarEmpleadosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/buscarEmpleados.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String campo = request.getParameter("campo");
        String valor = request.getParameter("valor");
        String columna;
        switch (campo == null ? "" : campo) {
            case "dni": columna = "dni"; break;
            case "sexo": columna = "sexo"; break;
            case "categoria": columna = "categoria"; break;
            case "anyos": columna = "anyos"; break;
            default: columna = "nombre";
        }

        String sql = "SELECT id,nombre,dni,sexo,categoria,anyos FROM empleados WHERE " + columna + " LIKE ? ORDER BY nombre";
        List<EmpleadoWeb> lista = new ArrayList<>();
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (valor == null ? "" : valor.trim()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new EmpleadoWeb(rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"),
                            rs.getString("sexo"), rs.getInt("categoria"), rs.getInt("anyos")));
                }
            }
            request.setAttribute("empleados", lista);
            request.setAttribute("busqueda", valor);
            request.getRequestDispatcher("/resultadosBusqueda.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensajeError", "No se ha podido buscar. Comprueba la conexión con MariaDB.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
