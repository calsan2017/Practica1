<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Resultado del salario</title><link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css"></head>
<body><div class="contenedor">
<h1>Resultado de la consulta</h1>
<% if (request.getAttribute("mensaje") != null) { %>
<p class="mensaje"><%= request.getAttribute("mensaje") %></p>
<% } else if (request.getAttribute("dni") != null) { %>
<table><thead><tr><th>Nombre</th><th>DNI</th><th>Sueldo</th><th>Fecha de cálculo</th></tr></thead>
<tbody><tr><td><%= request.getAttribute("nombre") %></td><td><%= request.getAttribute("dni") %></td><td><%= request.getAttribute("sueldo") %></td><td><%= request.getAttribute("fecha") %></td></tr></tbody></table>
<% } %>
<p><a class="boton" href="salario.jsp">Volver</a> <a class="boton" href="${pageContext.request.contextPath}/index.jsp">Menú principal</a></p>
</div></body></html>
