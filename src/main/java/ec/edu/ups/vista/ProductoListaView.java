
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

public class ProductoListaView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JLabel lblBuscar;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(60, 60);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(null);
        panelSuperior.setPreferredSize(new Dimension(500, 70));

        lblBuscar = new JLabel();
        lblBuscar.setBounds(20, 20, 100, 25);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(120, 20, 150, 25);

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png"));
        btnBuscar.setBounds(290, 20, 80, 25);

        btnListar = new JButton(IconUtil.cargarIcono("user-list.png"));
        btnListar.setBounds(380, 20, 80, 25);

        panelSuperior.add(lblBuscar);
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnListar);

        modelo = new DefaultTableModel(new Object[]{"#", "#", "#"}, 0);
        tblProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblProductos);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    @Override
    public void actualizarTextos() {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.listar"));
        lblBuscar.setText(mensajes.get("global.nombre") + ":");
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnListar.setText(mensajes.get("producto.boton.listar"));

        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio")
        });
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblProductos() {
        return tblProductos;
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
