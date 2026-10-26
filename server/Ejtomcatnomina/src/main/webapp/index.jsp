<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empresa - Gestión de nóminas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
<div class="contenedor">
    <h1>Gestión de empleados y nóminas</h1>
    <p>Selecciona una opción del menú:</p>
    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}/empleados">Mostrar todos los empleados</a></li>
        <li><a href="salario.jsp">Consultar el salario de un empleado</a></li>
        <li><a href="${pageContext.request.contextPath}/buscarEmpleados">Buscar y modificar empleados</a></li>
    </ul>
</div>
</body>
</html>
