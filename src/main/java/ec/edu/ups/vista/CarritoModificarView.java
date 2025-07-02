package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTable tblItems;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private JButton btnModificar;
    private JLabel lblTitulo;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));


        setContentPane(panelPrincipal);
        setSize(600, 400);

        URL urlBuscar=getClass().getResource("/search.png");
        URL urlModificar=getClass().getResource("/edit.png");

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        tblItems.setModel(modeloDetalles);

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnModificar.setIcon(new ImageIcon(urlModificar));

        actualizarTextos();
        configurarListeners();

        btnModificar.setEnabled(false);
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.modificar.titulo.app"));
        lblTitulo.setText(mensajes.get("carrito.modificar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblFecha.setText(mensajes.get("global.fecha") + ":");
        lblItems.setText(mensajes.get("global.item") + ":");

        txtCodigo.setToolTipText(mensajes.get("carrito.top.codigo"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));

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

    private void configurarListeners() {
        modeloDetalles.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                actualizarSubtotalFila(e.getFirstRow());
            }
        });
    }

    private void actualizarSubtotalFila(int fila) {
        if (carritoActual != null) {
            try {
                int codigoProducto = (int) modeloDetalles.getValueAt(fila, 0);
                int nuevaCantidad = Integer.parseInt(modeloDetalles.getValueAt(fila, 3).toString());

                carritoActual.actualizarCantidadProducto(codigoProducto, nuevaCantidad);

                ItemCarrito itemActualizado = encontrarItem(codigoProducto);
                if (itemActualizado != null) {
                    modeloDetalles.setValueAt(
                            FormateadorUtils.formatearMoneda(itemActualizado.getSubtotal(), locale),
                            fila, 4);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje(mensajes.get("mensaje.carrito.cantidadInvalida"));
            }
        }
    }

    private ItemCarrito encontrarItem(int codigoProducto) {
        if (carritoActual == null) return null;
        for (ItemCarrito item : carritoActual.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                return item;
            }
        }
        return null;
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
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JButton getBtnModificar() { return btnModificar; }
    public Carrito getCarritoActual() { return carritoActual; }
}