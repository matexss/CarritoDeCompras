package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.FormateadorUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblItems;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private JLabel lblTitulo;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        URL urlBuscar= getClass().getResource("/search.png");
        URL urlEliminar = getClass().getResource("/trash.png");
        setContentPane(panelPrincipal);
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnEliminar.setIcon(new ImageIcon(urlEliminar));
        setSize(600, 400);


        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblItems.setModel(modeloDetalles);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.eliminar.titulo.app"));

        lblTitulo.setText(mensajes.get("carrito.eliminar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblUsuario.setText(mensajes.get("global.usuario"));
        lblFecha.setText(mensajes.get("global.fecha"));
        lblItems.setText(mensajes.get("global.item"));
        txtCodigo.setToolTipText(mensajes.get("carrito.top.codigo"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));

        Object[] columnasDetalles = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        mostrarItemsCarrito(carritoActual);
    }

    public void mostrarItemsCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            List<ItemCarrito> items = carrito.obtenerItems();
            for (ItemCarrito item : items) {
                Object[] fila = {
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                };
                modeloDetalles.addRow(fila);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTable getTblItems() { return tblItems; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public Carrito getCarritoActual() { return carritoActual; }
}