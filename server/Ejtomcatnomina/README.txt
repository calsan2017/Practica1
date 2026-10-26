PROYECTO TOMCAT_GESTION

Proyecto web para gestionar empleados y consultar sus nóminas. Mantiene los nombres de las clases del ejercicio original y añade las clases necesarias para Tomcat.

FUNCIONES
- Mostrar empleados.
- Buscar por nombre, DNI, sexo, categoría o años.
- Modificar nombre, DNI, sexo, categoría y años.
- Al guardar una modificación, calcular y actualizar el sueldo.
- Consultar la nómina por DNI.

BASE DE DATOS
El proyecto utiliza MariaDB en localhost:3306, base de datos gestion_nominas, usuario root y contraseña 123456. Si tu contraseña es diferente, cambia PASSWORD en src/main/java/Laboral/conexion.java.

IMPORTANTE SOBRE TABLAS
Este proyecto está preparado para la estructura que se venía usando en Tomcat: empleados(id, nombre, dni, sexo, categoria, anyos) y nominas(id, empleado_id, sueldo, fecha_calculo). La columna sexo debe aceptar H y M. Si tu base de datos tiene columnas distintas, no ejecutes scripts que borren datos; revisa la estructura antes.

EJECUTAR
1. Abre la carpeta tomcat_gestion en IntelliJ como proyecto Maven.
2. Pulsa Reload Maven para descargar dependencias.
3. Usa Tomcat 10.1 o superior (Jakarta Servlet 6 necesita Tomcat 10.1).
4. Configura el despliegue como tomcat_gestion:war exploded o despliega target/tomcat_gestion.war.
5. Abre http://localhost:8080/tomcat_gestion/

El proyecto no incluye una copia de tu base de datos ni cambia tus datos existentes.
