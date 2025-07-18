
package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class ProductoEliminarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton btnEliminar;
    private JTable tblProductos;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;

    private JLabel lblNombre;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(50, 50);

        Color fondo = new Color(255, 228, 232);
        panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(null);
        panelSuperior.setPreferredSize(new Dimension(600, 90));
        panelSuperior.setBackground(fondo);

        JLabel titulo = new JLabel("Eliminar Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBounds(200, 5, 300, 30);
        panelSuperior.add(titulo);

        lblNombre = new JLabel();
        lblNombre.setBounds(20, 45, 100, 25);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 45, 200, 25);

        btnEliminar = new JButton(IconUtil.cargarIcono("eliminar.png"));
        btnEliminar.setBounds(350, 45, 120, 25);

        panelSuperior.add(lblNombre);
        panelSuperior.add(txtNombre);
        panelSuperior.add(btnEliminar);

        modelo = new DefaultTableModel(new Object[]{"#", "#", "#"}, 0);
        tblProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblProductos);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }


    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.eliminar"));
        lblNombre.setText(mensajes.get("global.nombre") + ":");
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio")
        });
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }
}
