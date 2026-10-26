<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Consultar salario</title><link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css"></head>
<body><div class="contenedor">
<h1>Consultar salario</h1>
<form action="${pageContext.request.contextPath}/salario" method="post">
    <label for="dni">DNI del empleado:</label>
    <input type="text" id="dni" name="dni" required>
    <button type="submit">Buscar salario</button>
</form>
<p><a class="boton" href="${pageContext.request.contextPath}/index.jsp">Volver</a></p>
</div></body></html>
