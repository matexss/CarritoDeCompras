package ec.edu.ups.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase de utilidad para cargar y escalar iconos desde el paquete de recursos del proyecto.
 * Facilita la integración de imágenes en componentes Swing, especialmente en botones y menús.
 *
 * <p>Los iconos deben estar ubicados dentro del directorio <code>resources/iconos/</code>
 * del proyecto.</p>
 *
 * <p>Ejemplo de uso:<br>
 * <code>ImageIcon icono = IconUtil.cargarIcono("guardar.png");</code></p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class IconUtil {

    /**
     * Carga un icono con tamaño estándar (18x18 píxeles).
     *
     * @param nombreArchivo Nombre del archivo de icono (ej. "guardar.png").
     * @return Objeto {@link ImageIcon} escalado o un icono vacío si no se encuentra el recurso.
     */
    public static ImageIcon cargarIcono(String nombreArchivo) {
        return cargarIcono(nombreArchivo, 18, 18);
    }

    /**
     * Carga un icono con dimensiones personalizadas.
     *
     * @param nombreArchivo Nombre del archivo de icono (ej. "editar.png").
     * @param ancho         Ancho deseado en píxeles.
     * @param alto          Alto deseado en píxeles.
     * @return Objeto {@link ImageIcon} escalado o un icono vacío si no se encuentra el recurso.
     */
    public static ImageIcon cargarIcono(String nombreArchivo, int ancho, int alto) {
        URL recurso = IconUtil.class.getClassLoader().getResource("iconos/" + nombreArchivo);
        if (recurso != null) {
            ImageIcon iconoOriginal = new ImageIcon(recurso);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } else {
            System.err.println("❌ Icono no encontrado: " + nombreArchivo);
            return new ImageIcon(); // icono vacío para evitar crash visual
        }
    }
}
