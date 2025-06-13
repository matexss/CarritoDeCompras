package ec.edu.ups.vista;

import javax.swing.*;

public class CarritoView extends JFrame{
    private JPanel panel1;
    private JTextField txtListadoP;
    private JTextField txtCantidad;
    private JLabel lblListaProducto;
    private JLabel lblCantidad;
    private JButton btnFinalizar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JComboBox comboBox1;

    public CarritoView() {
        setContentPane(panel1);
        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //pack();
        txtListadoP.setPreferredSize(new java.awt.Dimension(80, 90));
        txtCantidad.setPreferredSize(new java.awt.Dimension(80, 90));


    }
}
