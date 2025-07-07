package ec.edu.ups.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println(" Clave faltante: '" + key + "' para idioma: " + locale);
            return "!" + key + "!";
        }
    }


    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public void cambiarLocale(Locale nuevoLocale) {
        this.locale = nuevoLocale;
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getMensaje(String clave) {
        return get(clave);
    }

    public void verificarClavesFaltantes(String rutaListaBase) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(rutaListaBase)) {

            if (is == null) {
                System.out.println(" Archivo de claves requeridas no encontrado: " + rutaListaBase);
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
                System.out.println(" Todas las claves están presentes para " + locale);
            } else {
                System.out.println(" Claves faltantes para " + locale + ":");
                faltantes.forEach(k -> System.out.println("   - " + k));
            }

        } catch (IOException e) {
            System.out.println("Error leyendo la lista de claves: " + e.getMessage());
        }
    }
    public void cambiarIdioma(String lang, String country) {
        locale = new Locale(lang, country);
        bundle = ResourceBundle.getBundle("mensajes", locale);
    }


}
