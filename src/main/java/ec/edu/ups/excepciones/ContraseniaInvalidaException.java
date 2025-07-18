package ec.edu.ups.excepciones;

/**
 * Excepción personalizada que se lanza cuando una contraseña no cumple con
 * los requisitos de seguridad definidos por el sistema.
 * <p>
 * Esta clase permite manejar validaciones estrictas de contraseñas seguras,
 * como longitud mínima, uso de mayúsculas, minúsculas, caracteres especiales, etc.
 * </p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class ContraseniaInvalidaException extends Exception {

    /**
     * Constructor por defecto. Lanza la excepción con un mensaje estándar.
     */
    public ContraseniaInvalidaException() {
        super("La contraseña no cumple con los requisitos de seguridad.");
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje detallado que explica el motivo del error.
     */
    public ContraseniaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
