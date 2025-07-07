package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class CarritoEliminarView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private JTextField txtCodigo, txtUsuario, txtFecha;
    private JButton btnBuscar, btnEliminar;
    private JTable tblItems;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    public CarritoEliminarView(CarritoController carritoController,
                               MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.carritoController = carritoController;
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel sup = new JPanel(new GridLayout(4, 2, 10, 8));
        JLabel lblCodigo = new JLabel();
        txtCodigo = new JTextField();
        btnBuscar = new JButton(IconUtil.cargarIcono("search.png"));
        JLabel lblUsuario = new JLabel();
        txtUsuario = new JTextField(); txtUsuario.setEditable(false);
        JLabel lblFecha = new JLabel();
        txtFecha = new JTextField();   txtFecha.setEditable(false);

        sup.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        sup.add(lblCodigo); sup.add(txtCodigo);
        sup.add(new JLabel()); sup.add(btnBuscar);
        sup.add(lblUsuario);  sup.add(txtUsuario);
        sup.add(lblFecha);    sup.add(txtFecha);
        add(sup, BorderLayout.NORTH);

        modeloDetalles = new DefaultTableModel();
        tblItems = new JTable(modeloDetalles);
        add(new JScrollPane(tblItems), BorderLayout.CENTER);

        btnEliminar = new JButton(IconUtil.cargarIcono("carrito-eliminar.png"));
        JPanel inf = new JPanel(); inf.add(btnEliminar);
        add(inf, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarCarrito());
        btnEliminar.addActionListener(e -> eliminarCarrito());
    }

    private void buscarCarrito() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            carritoActual = carritoController.buscarCarrito(codigo);
            if (carritoActual == null) {
                mostrarMensaje(mensajes.get("carrito.buscar.noencontrado"));
                return;
            }
            txtUsuario.setText(carritoActual.getUsuario().getUsername());
            txtFecha.setText(FormateadorUtils.formatearFecha(
                    carritoActual.getFechaCreacion().getTime(), locale));
            mostrarItemsCarrito(carritoActual);
        } catch (NumberFormatException ex) {
            mostrarMensaje(mensajes.get("global.codigo") + " inválido.");
        }
    }

    private void eliminarCarrito() {
        if (carritoActual == null) return;
        carritoController.eliminarCarrito(carritoActual.getCodigo());
        mostrarMensaje(mensajes.get("carrito.eliminar.exito"));
        modeloDetalles.setRowCount(0);
        txtCodigo.setText(""); txtUsuario.setText(""); txtFecha.setText("");
        carritoActual = null;
    }

    @Override public void actualizarTextos() {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("carrito.eliminar.titulo.app"));
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(0))
                .setText(mensajes.get("global.codigo")+":");
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(4))
                .setText(mensajes.get("global.usuario")+":");
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(6))
                .setText(mensajes.get("global.fecha")+":");
        btnBuscar.setToolTipText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
        modeloDetalles.setColumnIdentifiers(new Object[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        });
    }

    private void mostrarItemsCarrito(Carrito c) {
        modeloDetalles.setRowCount(0);
        for (ItemCarrito it : c.obtenerItems()) {
            modeloDetalles.addRow(new Object[]{
                    it.getProducto().getCodigo(),
                    it.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(it.getProducto().getPrecio(), locale),
                    it.getCantidad(),
                    FormateadorUtils.formatearMoneda(it.getSubtotal(), locale)
            });
        }
    }
    private void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m, mensajes.get("yesNo.app.titulo"),
                JOptionPane.INFORMATION_MESSAGE);
    }
}
