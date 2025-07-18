package ec.edu.ups.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Clase encargada de manejar la internacionalización de mensajes mediante archivos .properties
 * según el idioma y país configurados. Utiliza {@link ResourceBundle} para cargar los mensajes.
 *
 * <p>Permite obtener textos traducidos según la clave y cambiar dinámicamente el idioma de la aplicación.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Constructor que inicializa el handler con el idioma y país especificados.
     *
     * @param lenguaje Código de idioma (ej. "es", "en", "fr").
     * @param pais     Código de país (ej. "EC", "US", "FR").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje correspondiente a una clave específica.
     * Si la clave no existe, se devuelve un texto con signos de exclamación.
     *
     * @param key Clave del mensaje.
     * @return Mensaje traducido o "!clave!" si no existe.
     */
    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("❗ Clave faltante: '" + key + "' para idioma: " + locale);
            return "!" + key + "!";
        }
    }

    /**
     * Establece un nuevo idioma y país para los mensajes.
     *
     * @param lenguaje Código del nuevo idioma.
     * @param pais     Código del nuevo país.
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Cambia el locale completo (idioma y país) del handler.
     *
     * @param nuevoLocale Nuevo {@link Locale} a utilizar.
     */
    public void cambiarLocale(Locale nuevoLocale) {
        this.locale = nuevoLocale;
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Devuelve el {@link Locale} actual configurado.
     *
     * @return Locale activo.
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Obtiene el mensaje correspondiente a la clave especificada.
     * Es un alias de {@link #get(String)}.
     *
     * @param clave Clave del mensaje.
     * @return Mensaje traducido.
     */
    public String getMensaje(String clave) {
        return get(clave);
    }

    /**
     * Verifica si faltan claves en el archivo de mensajes actual en comparación con una lista base.
     *
     * @param rutaListaBase Ruta del archivo de texto plano que contiene claves esperadas, línea por línea.
     */
    public void verificarClavesFaltantes(String rutaListaBase) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(rutaListaBase)) {

            if (is == null) {
                System.out.println("❌ Archivo de claves requeridas no encontrado: " + rutaListaBase);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            List<String> faltantes = new ArrayList<>();

            br.lines().forEach(clave -> {
                String k = clave.trim();
                if (!k.isBlank() && !bundle.containsKey(k)) {
                    faltantes.add(k);
                }
            });

            if (faltantes.isEmpty()) {
                System.out.println("✅ Todas las claves están presentes para " + locale);
            } else {
                System.out.println("🔍 Claves faltantes para " + locale + ":");
                faltantes.forEach(k -> System.out.println("   - " + k));
            }

        } catch (IOException e) {
            System.out.println("⚠️ Error leyendo la lista de claves: " + e.getMessage());
        }
    }

    /**
     * Cambia el idioma actual usando códigos de idioma y país.
     *
     * @param lang    Código del idioma (ej. "es").
     * @param country Código del país (ej. "EC").
     */
    public void cambiarIdioma(String lang, String country) {
        locale = new Locale(lang, country);
        bundle = ResourceBundle.getBundle("mensajes", locale);
    }
}
