package Laboral;

/** Guarda los datos de un empleado para mostrarlos en las páginas JSP. */
public class EmpleadoWeb {
    private int id;
    private String nombre;
    private String dni;
    private String sexo;
    private int categoria;
    private int anyos;

    public EmpleadoWeb(int id, String nombre, String dni, String sexo, int categoria, int anyos) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getSexo() { return sexo; }
    public int getCategoria() { return categoria; }
    public int getAnyos() { return anyos; }
}
