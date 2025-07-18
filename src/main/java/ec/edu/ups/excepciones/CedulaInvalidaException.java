package ec.edu.ups.excepciones;

/**
 * Excepción personalizada que se lanza cuando una cédula ingresada es inválida.
 * <p>
 * Esta excepción puede utilizarse para validar que un número de cédula ecuatoriana
 * cumpla con el formato y lógica correspondiente.
 * </p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class CedulaInvalidaException extends Exception {

    /**
     * Constructor por defecto. Lanza la excepción con un mensaje predeterminado.
     */
    public CedulaInvalidaException() {
        super("La cédula ingresada no es válida.");
    }

    /**
     * Constructor que permite especificar un mensaje personalizado.
     *
     * @param mensaje Mensaje detallado del error de validación.
     */
    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
