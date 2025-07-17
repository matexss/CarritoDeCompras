package ec.edu.ups.util;

import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.ContraseniaInvalidaException;

public class ValidadorUsuario {

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

    public static void validarCampoObligatorio(String campo, String nombre) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + nombre + "' es obligatorio.");
        }
    }
    public static void validarCorreo(String correo) {
        if (correo == null || !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }
    }

    public static void validarTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^\\d{7,10}$")) {
            throw new IllegalArgumentException("Teléfono inválido. Solo se aceptan números de 7 a 10 dígitos.");
        }
    }

}
