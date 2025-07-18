package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria que proporciona métodos para formatear valores de tipo moneda y fecha
 * según la configuración regional ({@link Locale}).
 *
 * <p>Estos métodos son útiles para adaptar la salida visual de montos y fechas
 * a diferentes idiomas y países en aplicaciones internacionalizadas.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class FormateadorUtils {

    /**
     * Formatea un valor numérico como moneda según la configuración regional dada.
     *
     * @param cantidad Valor numérico a formatear.
     * @param locale   Configuración regional (ej. {@code new Locale("es", "EC")}).
     * @return Cadena con el valor formateado como moneda (ej. "$ 12,50").
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha según la configuración regional dada, usando estilo MEDIUM.
     *
     * @param fecha  Objeto {@link Date} a formatear.
     * @param locale Configuración regional deseada.
     * @return Cadena con la fecha formateada (ej. "18-jul-2025").
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}
