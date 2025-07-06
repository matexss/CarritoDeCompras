package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class UsuarioListarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JLabel lblListado;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTextField textField1;
    private JLabel lblTitulo;
    private DefaultTableModel tableModel;
    private Locale locale;
    private List<Usuario> listaActual;
    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioListarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        initComponents();
        configurarTabla();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(600, 400);
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        setContentPane(panelPrincipal);

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        JPanel panelSuperior = new JPanel(new FlowLayout());

        lblListado = new JLabel("Usuario:");
        txtUsuario = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnListar = new JButton("Listar");

        panelSuperior.add(lblListado);
        panelSuperior.add(txtUsuario);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnListar);

        panelCentro.add(panelSuperior, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tblUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblUsuarios);

        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblUsuarios.setModel(tableModel);
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setTitle(mensajes.get("usuario.listar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.listar.titulo.app"));
        lblListado.setText(mensajes.get("global.usuario") + ":");
        btnListar.setText(mensajes.get("menu.usuario.listar"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.get("global.usuario"),
                mensajes.get("global.rol")
        });
        mostrarUsuarios(listaActual);
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        this.listaActual = usuarios;
        tableModel.setRowCount(0);
        if (usuarios == null) return;
        for (Usuario usuario : usuarios) {
            String rolTraducido = usuario.getRol() == Rol.ADMINISTRADOR
                    ? mensajes.get("global.rol.admin")
                    : mensajes.get("global.rol.user");

            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    rolTraducido
            });
        }
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
