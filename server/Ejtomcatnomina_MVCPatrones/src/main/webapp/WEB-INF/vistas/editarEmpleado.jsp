<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Laboral.modelo.EmpleadoWeb" %>
<%
    EmpleadoWeb empleado = (EmpleadoWeb) request.getAttribute("empleado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar empleado</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
<div class="contenedor">
    <h1>Modificar empleado</h1>
    <p>El nombre y el DNI no se pueden modificar. Cambia los demás datos y pulsa «Guardar cambios». El sueldo se recalculará automáticamente.</p>
    <form action="${pageContext.request.contextPath}/controlador?accion=guardar" method="post">
        <input type="hidden" name="id" value="<%= empleado.getId() %>">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" value="<%= empleado.getNombre() %>" readonly>
        <label for="dni">DNI:</label>
        <input type="text" id="dni" value="<%= empleado.getDni() %>" readonly>
        <label for="sexo">Sexo (m/f):</label>
        <select id="sexo" name="sexo" required>
            <option value="m" <%= "m".equalsIgnoreCase(empleado.getSexo()) ? "selected" : "" %>>m</option>
            <option value="f" <%= "f".equalsIgnoreCase(empleado.getSexo()) ? "selected" : "" %>>f</option>
        </select>
        <label for="categoria">Categoría (1-10):</label>
        <input type="number" id="categoria" name="categoria" min="1" max="10" value="<%= empleado.getCategoria() %>" required>
        <label for="anyos">Años trabajados:</label>
        <input type="number" id="anyos" name="anyos" min="0" value="<%= empleado.getAnyos() %>" required>
        <button type="submit">Guardar cambios</button>
    </form>
    <p><a class="boton" href="${pageContext.request.contextPath}/controlador?accion=formularioBuscar">Cancelar y volver</a></p>
</div>
</body>
</html>
