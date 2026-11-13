package Laboral.dao;


public class EmpleadoDAOFactory {

    private EmpleadoDAOFactory() {

    }

    public static EmpleadoDAO crearEmpleadoDAO() {
        return new EmpleadoDAO();
    }
}
