package ec.edu.ups.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconUtil {

    public static ImageIcon cargarIcono(String nombreArchivo) {
        return cargarIcono(nombreArchivo, 18, 18); // tamaño estándar
    }

    public static ImageIcon cargarIcono(String nombreArchivo, int ancho, int alto) {
        URL recurso = IconUtil.class.getClassLoader().getResource("iconos/" + nombreArchivo);
        if (recurso != null) {
            ImageIcon iconoOriginal = new ImageIcon(recurso);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } else {
            System.err.println("❌ Icono no encontrado: " + nombreArchivo);
            return new ImageIcon(); // icono vacío para evitar crash
        }
    }
}
