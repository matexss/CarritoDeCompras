package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class ProductoModificarView extends JInternalFrame implements ActualizableConIdioma {

    private JTextField txtCodigo;
    private JButton buscarButton;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JComboBox<String> cbxOpciones;
    private JTextField txtModificar;
    private JLabel lblMensaje;
    private JButton btnModificar;
    private JPanel panelPrincipal;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        setSize(800, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(100, 100);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Modificar Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        txtCodigo = new JTextField(12);
        panel.add(txtCodigo, gbc);

        gbc.gridx = 2;
        buscarButton = new JButton("Buscar", IconUtil.cargarIcono("search.png", 18, 18));
        buscarButton.setPreferredSize(new Dimension(140, 30));
        panel.add(buscarButton, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nombre actual:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        lblNombre = new JLabel("---");
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD));
        panel.add(lblNombre, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Precio actual:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        lblPrecio = new JLabel("---");
        lblPrecio.setFont(lblPrecio.getFont().deriveFont(Font.BOLD));
        panel.add(lblPrecio, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        lblMensaje = new JLabel("Seleccione qué desea modificar:");
        panel.add(lblMensaje, gbc);

        gbc.gridx = 1;
        cbxOpciones = new JComboBox<>();
        cbxOpciones.setEnabled(false);
        panel.add(cbxOpciones, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nuevo valor:"), gbc);

        gbc.gridx = 1;
        txtModificar = new JTextField(15);
        txtModificar.setEditable(false);
        panel.add(txtModificar, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        btnModificar = new JButton("Modificar", IconUtil.cargarIcono("editar.png", 18, 18));
        btnModificar.setPreferredSize(new Dimension(150, 35));
        panel.add(btnModificar, gbc);

        setContentPane(panel);
    }

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.modificar"));

        buscarButton.setText(mensajes.get("global.boton.buscar"));
        lblMensaje.setText(mensajes.get("producto.msg.datoAModificar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));

        cbxOpciones.removeAllItems();
        cbxOpciones.addItem("Modificar Nombre");
        cbxOpciones.addItem("Modificar Codigo");
        cbxOpciones.addItem("Modificar Precio");
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBuscarButton() { return buscarButton; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public JTextField getTxtModificar() { return txtModificar; }
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblPrecio() { return lblPrecio; }
    public JComboBox<String> getCbxOpciones() { return cbxOpciones; }
    public JLabel getLblMensaje() { return lblMensaje; }
    public JButton getBtnModificar() { return btnModificar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
