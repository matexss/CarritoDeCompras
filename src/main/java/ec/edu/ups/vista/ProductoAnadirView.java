package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAceptar;
    private JButton btnLimpiar;

    public ProductoAnadirView() {
        super("Añadir Producto", true, true, true, true);
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(30, 30);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 100, 25);
        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 20, 200, 25);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 200, 25);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(20, 100, 100, 25);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 100, 200, 25);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(70, 150, 100, 30);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(210, 150, 100, 30);

        panelPrincipal.add(lblCodigo);
        panelPrincipal.add(txtCodigo);
        panelPrincipal.add(lblNombre);
        panelPrincipal.add(txtNombre);
        panelPrincipal.add(lblPrecio);
        panelPrincipal.add(txtPrecio);
        panelPrincipal.add(btnAceptar);
        panelPrincipal.add(btnLimpiar);

        setContentPane(panelPrincipal);
    }

    // Getters para controlador
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    // Mostrar mensajes emergentes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Limpiar campos
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
