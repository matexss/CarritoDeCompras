package ec.edu.ups.util;

import javax.swing.*;
import java.awt.*;

public final class IconUtil {

    private IconUtil() {} // Constructor privado

    public static ImageIcon icon(String nombreArchivo, int ancho, int alto) {
        java.net.URL url = IconUtil.class
                .getClassLoader()
                .getResource("imagenes/" + nombreArchivo);

        if (url == null) {
            System.err.println("No se encontró el ícono: " + nombreArchivo);
            return null;
        }

        Image img = new ImageIcon(url).getImage()
                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }
}
