package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoAnadirView extends JInternalFrame {

    private JTextField txtNombre;
    private JButton btnBuscar;
    private JLabel lblNombre;
    private JTextField txtCodigo;
    private JTextField txtPrecio;
    private JLabel lblCodigo;
    private JLabel lblPrecio;
    private JButton btnAñadir;
    private JButton btnGuardar;
    private JButton btnBuscar2;
    private JTable table1;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JScrollPane tblProductos;
    private JLabel lblSubtotal;
    private JPanel panelPrincipal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JComboBox comboBox1;
    private JLabel lblCantidad;
    private DefaultTableModel modelo;


    public CarritoAnadirView() {
        setContentPane(panelPrincipal);
        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Nombre", "Precio", "Cantidad", "Total"};
        modelo.setColumnIdentifiers(columnas);
        table1.setModel(modelo);
    }

    // Getters
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAñadir() {
        return btnAñadir;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }
}
