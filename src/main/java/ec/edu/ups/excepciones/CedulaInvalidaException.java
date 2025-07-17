package ec.edu.ups.excepciones;

public class CedulaInvalidaException extends Exception {

    public CedulaInvalidaException() {
        super("La cédula ingresada no es válida.");
    }

    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
