<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Error</title><link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css"></head>
<body><div class="contenedor">
<h1>Ha ocurrido un error</h1>
<p class="mensaje"><%= request.getAttribute("mensajeError") == null ? "No se ha podido realizar la operación." : request.getAttribute("mensajeError") %></p>
<p>Comprueba que MariaDB está iniciado y que la base de datos y las tablas existen.</p>
<p><a class="boton" href="${pageContext.request.contextPath}/index.jsp">Volver al menú</a></p>
</div></body></html>
