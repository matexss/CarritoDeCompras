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
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;
    private JPanel panelPrincipal;
    private JTextField txtCodigo, txtUsuario, txtFecha;
    private JButton btnBuscar, btnEliminar;
    private JTable tblItems;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    private JPanel panelSuperior;

    public CarritoEliminarView(CarritoController carritoController,
                               MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.carritoController = carritoController;
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        Color fondo = new Color(235, 248, 255);
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        setSize(600, 430);

        JLabel titulo = new JLabel("Eliminar Carrito", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(0, 87, 146));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        panelSuperior = new JPanel(new GridLayout(4, 2, 10, 8));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelSuperior.setBackground(fondo);

        JLabel lblCodigo = new JLabel();
        txtCodigo = new JTextField();
        btnBuscar = new JButton(IconUtil.cargarIcono("search.png"));
        JLabel lblUsuario = new JLabel();
        txtUsuario = new JTextField(); txtUsuario.setEditable(false);
        JLabel lblFecha = new JLabel();
        txtFecha = new JTextField();   txtFecha.setEditable(false);

        panelSuperior.add(lblCodigo); panelSuperior.add(txtCodigo);
        panelSuperior.add(new JLabel()); panelSuperior.add(btnBuscar);
        panelSuperior.add(lblUsuario);  panelSuperior.add(txtUsuario);
        panelSuperior.add(lblFecha);    panelSuperior.add(txtFecha);
        add(panelSuperior, BorderLayout.NORTH);

        modeloDetalles = new DefaultTableModel();
        tblItems = new JTable(modeloDetalles);
        JScrollPane scrollPane = new JScrollPane(tblItems);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detalles del carrito"));
        add(scrollPane, BorderLayout.CENTER);

        btnEliminar = new JButton(IconUtil.cargarIcono("carrito-eliminar.png"));
        JPanel inf = new JPanel();
        inf.setBackground(fondo);
        inf.add(btnEliminar);
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

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("carrito.eliminar.titulo.app"));

        ((JLabel) panelSuperior.getComponent(0)).setText(mensajes.get("global.codigo") + ":");
        ((JLabel) panelSuperior.getComponent(4)).setText(mensajes.get("global.usuario") + ":");
        ((JLabel) panelSuperior.getComponent(6)).setText(mensajes.get("global.fecha") + ":");

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
