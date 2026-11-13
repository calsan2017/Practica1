package Laboral.controlador;

import Laboral.modelo.EmpleadoWeb;
import Laboral.dao.EmpleadoDAO;
import Laboral.dao.EmpleadoDAOFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
 * FRONT CONTROLLER:
 * Todas las peticiones de la aplicación llegan a este único servlet.
 * Según el parámetro "accion", realiza la operación correspondiente.
 */
@WebServlet("/controlador")
public class ControladorServlet extends HttpServlet {

    private static final int[] SUELDO_BASE = {
            50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        procesarPeticion(request, response);
    }

    private void procesarPeticion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.isEmpty()) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                listarEmpleados(request, response);
                break;
            case "formularioBuscar":
                request.getRequestDispatcher("/WEB-INF/vistas/buscarEmpleados.jsp")
                        .forward(request, response);
                break;
            case "buscar":
                buscarEmpleados(request, response);
                break;
            case "formularioEditar":
                mostrarFormularioEditar(request, response);
                break;
            case "guardar":
                guardarCambios(request, response);
                break;
            case "formularioSalario":
                request.getRequestDispatcher("/WEB-INF/vistas/salario.jsp")
                        .forward(request, response);
                break;
            case "salario":
                consultarSalario(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La acción no existe.");
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EmpleadoDAO dao = EmpleadoDAOFactory.crearEmpleadoDAO();
            List<EmpleadoWeb> empleados = dao.listar();
            request.setAttribute("empleados", empleados);
            request.getRequestDispatcher("/WEB-INF/vistas/empleados.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            mostrarError(request, response, "No se han podido consultar los empleados.");
        }
    }

    private void buscarEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String valor = request.getParameter("valor");
            String campo = request.getParameter("campo");

            EmpleadoDAO dao = EmpleadoDAOFactory.crearEmpleadoDAO();
            request.setAttribute("busqueda", valor);
            request.setAttribute("empleados", dao.buscar(campo, valor));
            request.getRequestDispatcher("/WEB-INF/vistas/resultadosBusqueda.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            mostrarError(request, response, "No se ha podido realizar la búsqueda.");
        }
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            EmpleadoDAO dao = EmpleadoDAOFactory.crearEmpleadoDAO();
            EmpleadoWeb empleado = dao.obtener(id);

            if (empleado == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe ese empleado.");
                return;
            }

            request.setAttribute("empleado", empleado);
            request.getRequestDispatcher("/WEB-INF/vistas/editarEmpleado.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El identificador no es válido.");
        } catch (SQLException e) {
            mostrarError(request, response, "No se ha podido cargar el empleado.");
        }
    }

    private void guardarCambios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String sexo = request.getParameter("sexo");
            int categoria = Integer.parseInt(request.getParameter("categoria"));
            int anyos = Integer.parseInt(request.getParameter("anyos"));

            if (sexo == null) {
                mostrarError(request, response, "Debes seleccionar el sexo.");
                return;
            }

            sexo = sexo.trim().toLowerCase();

            if (!(sexo.equals("m") || sexo.equals("f")) || categoria < 1
                    || categoria > 10 || anyos < 0) {
                mostrarError(request, response,
                        "Revisa los datos. Sexo: m o f; categoría: 1-10; años: 0 o más.");
                return;
            }

            EmpleadoDAO dao = EmpleadoDAOFactory.crearEmpleadoDAO();

            // Recuperamos nombre y DNI de la base de datos.
            // No usamos valores enviados desde el formulario para estos dos campos.
            EmpleadoWeb empleadoActual = dao.obtener(id);

            if (empleadoActual == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe ese empleado.");
                return;
            }

            EmpleadoWeb empleado = new EmpleadoWeb(
                    id,
                    empleadoActual.getNombre(),
                    empleadoActual.getDni(),
                    sexo,
                    categoria,
                    anyos
            );

            int sueldo = SUELDO_BASE[categoria - 1] + 5000 * anyos;
            dao.actualizar(empleado, sueldo);

            response.sendRedirect(request.getContextPath() + "/controlador?accion=listar");

        } catch (NumberFormatException e) {
            mostrarError(request, response,
                    "El identificador, la categoría y los años deben ser números.");
        } catch (SQLException e) {
            mostrarError(request, response, "No se han podido guardar los cambios.");
        }
    }

    private void consultarSalario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni = request.getParameter("dni");

        if (dni == null || dni.trim().isEmpty()) {
            request.setAttribute("mensaje", "Escribe un DNI para realizar la búsqueda.");
            request.getRequestDispatcher("/WEB-INF/vistas/resultadoSalario.jsp")
                    .forward(request, response);
            return;
        }

        try {
            EmpleadoDAO dao = EmpleadoDAOFactory.crearEmpleadoDAO();
            EmpleadoDAO.NominaDatos nomina = dao.buscarNomina(dni.trim());

            if (nomina == null) {
                request.setAttribute("mensaje", "No se ha encontrado una nómina para ese DNI.");
            } else {
                request.setAttribute("nombre", nomina.getNombre());
                request.setAttribute("dni", nomina.getDni());
                request.setAttribute("sueldo", nomina.getSueldo());
                request.setAttribute("fecha", nomina.getFecha());
            }

            request.getRequestDispatcher("/WEB-INF/vistas/resultadoSalario.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            mostrarError(request, response, "No se ha podido consultar el salario.");
        }
    }

    private void mostrarError(HttpServletRequest request, HttpServletResponse response,
                              String mensaje) throws ServletException, IOException {
        request.setAttribute("mensajeError", mensaje);
        request.getRequestDispatcher("/WEB-INF/vistas/error.jsp")
                .forward(request, response);
    }
}
