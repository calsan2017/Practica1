package Laboral.excepciones;

public class DatosNoCorrectosException extends RuntimeException {
    /**
     *
     * @param message
     */
    public DatosNoCorrectosException(String message) {
        super(message);
    }
}
