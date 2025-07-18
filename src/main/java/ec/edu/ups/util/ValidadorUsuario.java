package ec.edu.ups.util;

import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.ContraseniaInvalidaException;

/**
 * Clase utilitaria para realizar validaciones sobre los datos de los usuarios,
 * como cédula ecuatoriana, contraseña, correo electrónico, teléfono y campos obligatorios.
 *
 * <p>Contiene métodos estáticos que lanzan excepciones personalizadas en caso de errores.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class ValidadorUsuario {

    /**
     * Valida si la cédula ecuatoriana ingresada es válida.
     * Verifica longitud, formato y dígito verificador.
     *
     * @param cedula Cédula a validar.
     * @throws CedulaInvalidaException Si la cédula no es válida.
     */
    public static void validarCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            throw new CedulaInvalidaException("La cédula debe contener 10 dígitos.");
        }

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
        }

        int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
        int decenaSuperior = ((suma + 9) / 10) * 10;
        int verificadorCalculado = decenaSuperior - suma;

        if (verificadorCalculado == 10) verificadorCalculado = 0;

        if (verificadorCalculado != ultimoDigito) {
            throw new CedulaInvalidaException("Cédula inválida. Dígito verificador incorrecto.");
        }
    }

    /**
     * Valida si la contraseña cumple con los requisitos de seguridad:
     * mínimo 6 caracteres, una mayúscula, una minúscula y un carácter especial (@ _ -).
     *
     * @param contrasenia Contraseña a validar.
     * @throws ContraseniaInvalidaException Si no cumple con los criterios.
     */
    public static void validarContrasenia(String contrasenia) throws ContraseniaInvalidaException {
        if (contrasenia == null || contrasenia.length() < 6) {
            throw new ContraseniaInvalidaException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (!contrasenia.matches(".*[A-Z].*")) {
            throw new ContraseniaInvalidaException("Debe incluir al menos una letra mayúscula.");
        }
        if (!contrasenia.matches(".*[a-z].*")) {
            throw new ContraseniaInvalidaException("Debe incluir al menos una letra minúscula.");
        }
        if (!contrasenia.matches(".*[@_\\-].*")) {
            throw new ContraseniaInvalidaException("Debe incluir al menos uno de los caracteres especiales: @ _ -");
        }
    }

    /**
     * Valida que un campo obligatorio no esté vacío ni nulo.
     *
     * @param campo  Valor del campo.
     * @param nombre Nombre descriptivo del campo.
     * @throws IllegalArgumentException Si el campo está vacío o nulo.
     */
    public static void validarCampoObligatorio(String campo, String nombre) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + nombre + "' es obligatorio.");
        }
    }

    /**
     * Valida que el correo electrónico tenga un formato válido.
     *
     * @param correo Correo a validar.
     * @throws IllegalArgumentException Si el formato del correo es inválido.
     */
    public static void validarCorreo(String correo) {
        if (correo == null || !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }
    }

    /**
     * Valida que el número de teléfono tenga entre 7 y 10 dígitos numéricos.
     *
     * @param telefono Teléfono a validar.
     * @throws IllegalArgumentException Si el teléfono es inválido.
     */
    public static void validarTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^\\d{7,10}$")) {
            throw new IllegalArgumentException("Teléfono inválido. Solo se aceptan números de 7 a 10 dígitos.");
        }
    }
}
