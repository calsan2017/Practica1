<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Buscar empleados</title><link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css"></head>
<body><div class="contenedor">
<h1>Buscar empleados</h1>
<form action="${pageContext.request.contextPath}/buscarEmpleados" method="post">
    <label for="campo">Buscar por:</label>
    <select name="campo" id="campo">
        <option value="nombre">Nombre</option>
        <option value="dni">DNI</option>
        <option value="sexo">Sexo</option>
        <option value="categoria">Categoría</option>
        <option value="anyos">Años trabajados</option>
    </select>
    <label for="valor">Dato a buscar:</label>
    <input type="text" id="valor" name="valor">
    <button type="submit">Buscar</button>
</form>
<p><a class="boton" href="${pageContext.request.contextPath}/index.jsp">Volver</a></p>
</div></body></html>
