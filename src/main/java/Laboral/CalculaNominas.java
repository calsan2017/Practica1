package Laboral;

public class CalculaNominas {

    public static void main()throws DatosNoCorrectosException {

        try{

            Empleado empleado = new Empleado("James Cosling","32000032G",'M', 4,7);
            Empleado empleado1 = new Empleado("Ada Lovelace","32000031R",'F');
            Nomina no = new Nomina();

            escribe(empleado, empleado1, no);
            empleado.setCategoria(9);
            empleado1.incrAnyos();

            System.out.println("Despues de modificacion: ");
            escribe(empleado, empleado1, no);


        }catch (DatosNoCorrectosException e){
            System.out.println(e.getMessage());
        }
    }


    private static void escribe (Empleado empleado, Empleado empleado1, Nomina no){

        empleado.Imprimir();
        System.out.println("Nomina empleado 1: " + no.sueldo(empleado));
        empleado1.Imprimir();
        System.out.println("Nomina empleado 2: " + no.sueldo(empleado1));

    }
}
