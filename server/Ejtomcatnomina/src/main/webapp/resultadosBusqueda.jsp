<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, Laboral.EmpleadoWeb" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Resultados de búsqueda</title><link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css"></head>
<body><div class="contenedor">
<h1>Resultados de la búsqueda</h1>
<p>Búsqueda: <%= request.getAttribute("busqueda") == null ? "" : request.getAttribute("busqueda") %></p>
<div class="tabla-responsive"><table>
<thead><tr><th>Nombre</th><th>DNI</th><th>Sexo</th><th>Categoría</th><th>Años trabajados</th><th>Acción</th></tr></thead>
<tbody>
<%
List<EmpleadoWeb> empleados = (List<EmpleadoWeb>) request.getAttribute("empleados");
if (empleados != null && !empleados.isEmpty()) {
    for (EmpleadoWeb e : empleados) {
%>
<tr><td><%= e.getNombre() %></td><td><%= e.getDni() %></td><td><%= e.getSexo() %></td><td><%= e.getCategoria() %></td><td><%= e.getAnyos() %></td>
<td><a class="boton" href="${pageContext.request.contextPath}/editarEmpleado?id=<%= e.getId() %>">Modificar</a></td></tr>
<%  }
} else { %>
<tr><td colspan="6">No se han encontrado empleados.</td></tr>
<% } %>
</tbody></table></div>
<p><a class="boton" href="${pageContext.request.contextPath}/buscarEmpleados">Volver a buscar</a> <a class="boton" href="${pageContext.request.contextPath}/index.jsp">Volver al menú</a></p>
</div></body></html>
