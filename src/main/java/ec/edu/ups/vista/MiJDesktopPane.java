package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Clase personalizada de {@link JDesktopPane} que representa el escritorio principal
 * del sistema con un fondo gráfico moderno y decorativo.
 *
 * <p>Esta clase se utiliza como contenedor de ventanas internas en la interfaz MDI.
 * El fondo incluye degradados, polígonos decorativos, óvalos y líneas inclinadas
 * para mejorar la estética visual del sistema de carrito de compras.</p>
 *
 * @author Mateo
 * @version 1.0
 */
public class MiJDesktopPane extends JDesktopPane {

    /**
     * Pinta el componente con un fondo personalizado usando gráficos vectoriales y transparencia.
     *
     * @param g el contexto gráfico del sistema.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Activar antialiasing para suavizar gráficos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo en degradado vertical (gris oscuro a gris más claro)
        GradientPaint fondo = new GradientPaint(0, 0, new Color(30, 30, 30), 0, h, new Color(60, 60, 60));
        g2.setPaint(fondo);
        g2.fillRect(0, 0, w, h);

        // Triángulo decorativo azul celeste translúcido
        g2.setColor(new Color(100, 149, 237, 80));
        int[] x1 = {0, w / 2, w};
        int[] y1 = {h / 3, h / 2 + 60, h / 3};
        g2.fillPolygon(x1, y1, 3);

        // Triángulo decorativo blanco translúcido en parte inferior
        g2.setColor(new Color(255, 255, 255, 30));
        int[] x2 = {0, w / 3, w};
        int[] y2 = {h - 100, h - 150, h - 80};
        g2.fillPolygon(x2, y2, 3);

        // Óvalo rosa en parte superior derecha
        g2.setColor(new Color(255, 105, 180, 90));
        g2.fillOval(w - 200, 50, 120, 120);

        // Óvalo celeste en esquina inferior izquierda
        g2.setColor(new Color(0, 255, 255, 60));
        g2.fillOval(80, h - 180, 160, 160);

        // Líneas diagonales de fondo blancas muy tenues
        g2.setColor(new Color(255, 255, 255, 25));
        for (int i = -200; i < w + 200; i += 40) {
            g2.drawLine(i, 0, i - 100, h);
        }

        // Texto decorativo principal del sistema
        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        g2.setColor(new Color(255, 255, 255, 50));
        g2.drawString("Carrito de Compras", 40, 60);
    }
}
