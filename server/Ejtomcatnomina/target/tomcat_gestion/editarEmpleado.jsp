<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Laboral.EmpleadoWeb" %>
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
    <p>Modifica los datos y pulsa «Guardar cambios». El sueldo se recalculará automáticamente.</p>
    <form action="${pageContext.request.contextPath}/editarEmpleado" method="post">
        <input type="hidden" name="id" value="<%= empleado.getId() %>">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" maxlength="100" value="<%= empleado.getNombre() %>" required>
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" maxlength="9" value="<%= empleado.getDni() %>" required>
        <label for="sexo">Sexo (H/M):</label>
        <select id="sexo" name="sexo" required>
            <option value="H" <%= "H".equalsIgnoreCase(empleado.getSexo()) ? "selected" : "" %>>H</option>
            <option value="M" <%= "M".equalsIgnoreCase(empleado.getSexo()) ? "selected" : "" %>>M</option>
        </select>
        <label for="categoria">Categoría (1-10):</label>
        <input type="number" id="categoria" name="categoria" min="1" max="10" value="<%= empleado.getCategoria() %>" required>
        <label for="anyos">Años trabajados:</label>
        <input type="number" id="anyos" name="anyos" min="0" value="<%= empleado.getAnyos() %>" required>
        <button type="submit">Guardar cambios</button>
    </form>
    <p><a class="boton" href="${pageContext.request.contextPath}/buscarEmpleados">Cancelar y volver</a></p>
</div>
</body>
</html>
