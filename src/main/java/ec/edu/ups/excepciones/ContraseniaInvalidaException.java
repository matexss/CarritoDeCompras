package ec.edu.ups.excepciones;

public class ContraseniaInvalidaException extends Exception {

    public ContraseniaInvalidaException() {
        super("La contraseña no cumple con los requisitos de seguridad.");
    }

    public ContraseniaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
