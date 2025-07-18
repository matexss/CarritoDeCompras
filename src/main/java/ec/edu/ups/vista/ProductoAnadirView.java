
package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class ProductoAnadirView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAceptar;
    private JButton btnLimpiar;

    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(30, 30);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);

        lblCodigo = new JLabel();
        lblCodigo.setBounds(20, 20, 100, 25);
        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 20, 200, 25);

        lblNombre = new JLabel();
        lblNombre.setBounds(20, 60, 100, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 200, 25);

        lblPrecio = new JLabel();
        lblPrecio.setBounds(20, 100, 100, 25);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 100, 200, 25);

        btnAceptar = new JButton(IconUtil.cargarIcono("logout.png"));
        btnAceptar.setBounds(70, 150, 100, 30);

        btnLimpiar = new JButton(IconUtil.cargarIcono("eliminar.png"));
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

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.anadir"));
        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblNombre.setText(mensajes.get("global.nombre") + ":");
        lblPrecio.setText(mensajes.get("global.precio") + ":");
        btnAceptar.setText(mensajes.get("global.boton.aceptar"));
        btnLimpiar.setText(mensajes.get("global.boton.limpiar"));
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
