package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton button1;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JButton btnListar;
    private JTable tblDetalles;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloDetalles;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblDetalles;
    private List<Carrito> listaActual;
    private Carrito carritoActual;
    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoListarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        URL urlListar=getClass().getResource("/list.png");
        URL urlBuscar=getClass().getResource("/search.png");

        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);

        btnListar.setIcon(new ImageIcon(urlListar));
        btnBuscar.setIcon(new ImageIcon(urlBuscar));

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.listar.titulo.app"));
        lblTitulo.setText(mensajes.get("carrito.listar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblDetalles.setText(mensajes.get("global.detalles"));

        txtCodigo.setToolTipText(mensajes.get("carrito.top.codigo"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnListar.setText(mensajes.get("menu.carrito.listar"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.usuario"),
                mensajes.get("global.fecha"),
                mensajes.get("global.item"),
                mensajes.get("global.subtotal"),
                mensajes.get("global.IVA"),
                mensajes.get("global.total")
        };
        modelo.setColumnIdentifiers(columnas);

        Object[] columnasDetalles = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        mostrarDetallesCarrito(carritoActual);
        mostrarCarritos(listaActual);

    }

    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual=carritos;
        modelo.setRowCount(0);
        if(carritos == null) {
            return;
        }
        limpiarTablaDetalles();
        for (Carrito carrito : carritos) {
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "N/A",
                    carrito.getFechaCreacion() != null ? FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale) : "N/A",
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            };
            modelo.addRow(fila);
        }
    }

    public void mostrarDetallesCarrito(Carrito carrito) {
        this.carritoActual=carrito;
        limpiarTablaDetalles();
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

    public void limpiarTablaDetalles() {
        modeloDetalles.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }
}