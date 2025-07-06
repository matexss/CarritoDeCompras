package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo degradado (cielo)
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(135, 206, 235); // Azul claro
        Color color2 = new Color(255, 255, 255); // Blanco
        GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight() / 2);

        // Césped en degradado
        Color grassColor1 = new Color(34, 139, 34); // Verde pasto
        Color grassColor2 = new Color(50, 205, 50); // Verde más claro
        GradientPaint grassGradient = new GradientPaint(0, getHeight() / 2, grassColor1, 0, getHeight(), grassColor2);
        g2d.setPaint(grassGradient);
        g2d.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);

        // Dibuja el sol
        g.setColor(Color.YELLOW);
        g.fillOval(getWidth() / 2 - 75, 50, 150, 150);

        // Dibuja nubes abstractas
        g.setColor(Color.WHITE);
        g.fillOval(100, 80, 120, 60);
        g.fillOval(150, 50, 130, 70);
        g.fillOval(300, 70, 150, 80);

        // Dibuja algunas montañas
        g.setColor(new Color(139, 69, 19)); // Marrón oscuro
        int[] xPoints = {0, getWidth() / 2, getWidth()};
        int[] yPoints = {getHeight() / 2, 100, getHeight() / 2};
        g.fillPolygon(xPoints, yPoints, 3);

        // Dibuja un árbol estilizado con círculos
        g.setColor(new Color(139, 69, 19)); // Tronco marrón
        g.fillRect(100, getHeight() / 2 - 80, 20, 60);
        g.setColor(new Color(0, 128, 0)); // Hojas verdes
        g.fillOval(90, getHeight() / 2 - 120, 50, 50); // Parte superior del árbol
        g.fillOval(70, getHeight() / 2 - 90, 50, 50); // Parte media
        g.fillOval(110, getHeight() / 2 - 60, 50, 50); // Parte inferior

        // Dibuja un segundo árbol
        g.setColor(new Color(139, 69, 19)); // Tronco marrón
        g.fillRect(300, getHeight() / 2 - 100, 20, 60);
        g.setColor(new Color(0, 128, 0)); // Hojas verdes
        g.fillOval(290, getHeight() / 2 - 140, 50, 50);
        g.fillOval(270, getHeight() / 2 - 110, 50, 50);
        g.fillOval(310, getHeight() / 2 - 80, 50, 50);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Paisaje Creativo");
        MiJDesktopPane panel = new MiJDesktopPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
    }
}
