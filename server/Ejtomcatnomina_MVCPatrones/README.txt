PROYECTO: GESTIÓN DE NÓMINAS - MVC Y FRONT CONTROLLER

ORGANIZACIÓN DE CARPETAS

src/main/java/Laboral/
  controlador/
    ControladorServlet.java
    Único servlet que recibe todas las peticiones.

  dao/
    EmpleadoDAO.java
    EmpleadoDAOFactory.java
    Consultas SQL y creación del DAO.

  modelo/
    EmpleadoWeb.java
    Datos de los empleados utilizados por la aplicación web.

  util/
    Conexion.java
    Conexión con MariaDB.

  excepciones/
    DatosNoCorrectosException.java
    Excepción propia conservada del ejercicio anterior.

src/main/webapp/
  index.jsp
  estilos.css
  WEB-INF/vistas/
    Páginas JSP de la aplicación.

archivos_no_utilizados/
  Clases antiguas o que no utiliza la aplicación web actual.
  Se guardan como copia y están fuera de src/main/java para que Maven
  no intente compilarlas.

PATRONES UTILIZADOS
1. MVC: separa modelo, vistas y controlador.
2. Front Controller: todas las peticiones pasan por ControladorServlet.
3. DAO: EmpleadoDAO centraliza las consultas SQL.
4. Factory: EmpleadoDAOFactory crea los objetos DAO.

MODIFICACIÓN DE EMPLEADOS
- Nombre y DNI aparecen como solo lectura.
- El controlador recupera nombre y DNI desde la base de datos.
- El DAO solo actualiza sexo, categoría y años trabajados.
- El sueldo se recalcula al guardar.

EJECUCIÓN
1. Abrir como proyecto Maven en IntelliJ.
2. Recargar las dependencias Maven.
3. Configurar Tomcat 10.1.
4. Ejecutar con el contexto /tomcat_gestion.
5. Abrir http://localhost:8080/tomcat_gestion/

La base de datos gestion_nominas debe existir y tener las tablas empleados y nominas.
Comprueba las credenciales de MariaDB en Laboral/util/Conexion.java.
