package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoEliminarView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton btnEliminar;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private JLabel lblCodigo;

    public ProductoEliminarView() {
        setContentPane(panelPrincipal);
        setTitle("Eliminar Producto");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setSize(600, 400);


        modelo = new DefaultTableModel();
        Object[] columnas = {"Id", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        for(Producto producto: productos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()};
            modelo.addRow(fila);
        }

    }
}
