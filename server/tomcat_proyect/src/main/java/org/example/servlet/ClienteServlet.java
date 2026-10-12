
package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.dao.ClienteDAO;
import org.example.modelo.Cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private final ClienteDAO dao = new ClienteDAO();

    // MOSTRAR LA PÁGINA
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String editar = request.getParameter("editar");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>CRUD de clientes</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:40px;background:#f4f4f4}");
            out.println("table{border-collapse:collapse;background:white;width:100%}");
            out.println("th,td{border:1px solid #ccc;padding:10px;text-align:left}");
            out.println("th{background:#ddd}");
            out.println("input{padding:8px;margin:5px}");
            out.println("button{padding:8px;cursor:pointer}");
            out.println("a{margin-right:8px}");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<h1>Gestión de clientes</h1>");

            Cliente clienteEditar = null;

            if (editar != null && !editar.isBlank()) {
                try {
                    clienteEditar = dao.buscar(Integer.parseInt(editar));
                } catch (NumberFormatException e) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "ID inválido"
                    );
                    return;
                }
            }

            int id = clienteEditar == null ? 0 : clienteEditar.getId();
            String nombre = clienteEditar == null
                    ? "" : clienteEditar.getNombre();
            String email = clienteEditar == null
                    ? "" : clienteEditar.getEmail();

            out.println("<h2>"
                    + (id == 0 ? "Añadir cliente" : "Editar cliente")
                    + "</h2>");

            out.println("<form method='post' action='"
                    + request.getContextPath() + "/clientes'>");

            out.println("<input type='hidden' name='id' value='"
                    + id + "'>");

            out.println("<input type='text' name='nombre' placeholder='Nombre' required value='"
                    + escapar(nombre) + "'>");

            out.println("<input type='email' name='email' placeholder='Email' required value='"
                    + escapar(email) + "'>");

            out.println("<button type='submit' name='accion' value='guardar'>"
                    + "Guardar</button>");

            if (id != 0) {
                out.println("<a href='" + request.getContextPath()
                        + "/clientes'>Cancelar</a>");
            }

            out.println("</form>");

            out.println("<h2>Lista de clientes</h2>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Nombre</th><th>Email</th><th>Acciones</th></tr>");

            try {
                List<Cliente> clientes = dao.listar();

                for (Cliente c : clientes) {
                    out.println("<tr>");
                    out.println("<td>" + c.getId() + "</td>");
                    out.println("<td>" + escapar(c.getNombre()) + "</td>");
                    out.println("<td>" + escapar(c.getEmail()) + "</td>");
                    out.println("<td>");

                    out.println("<a href='" + request.getContextPath()
                            + "/clientes?editar=" + c.getId()
                            + "'>Editar</a>");

                    out.println("<form method='post' action='"
                            + request.getContextPath()
                            + "/clientes' style='display:inline'>");

                    out.println("<input type='hidden' name='id' value='"
                            + c.getId() + "'>");

                    out.println("<button type='submit' name='accion' value='eliminar'>"
                            + "Eliminar</button>");

                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }

            } catch (SQLException e) {
                throw new ServletException(
                        "Error al consultar la base de datos", e
                );
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al cargar el cliente", e
            );
        }
    }

    // GUARDAR, ACTUALIZAR O ELIMINAR
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String accion = request.getParameter("accion");

            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(id);

            } else {
                String idTexto = request.getParameter("id");
                int id = (idTexto == null || idTexto.isBlank())
                        ? 0 : Integer.parseInt(idTexto);

                String nombre = request.getParameter("nombre");
                String email = request.getParameter("email");

                if (nombre == null || nombre.isBlank()
                        || email == null || email.isBlank()) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Nombre y email son obligatorios"
                    );
                    return;
                }

                Cliente cliente = new Cliente(id, nombre, email);

                if (id == 0) {
                    dao.insertar(cliente);
                } else {
                    dao.actualizar(cliente);
                }
            }

            response.sendRedirect(
                    request.getContextPath() + "/clientes"
            );

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al modificar la base de datos", e
            );
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID inválido"
            );
        }
    }

    // EVITAR QUE LOS DATOS SE INTERPRETEN COMO HTML
    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
