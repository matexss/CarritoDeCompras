package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
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

    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;
    private CarritoController carritoController;
    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoEliminarView(CarritoController carritoController, MensajeInternacionalizacionHandler mensajes) {
        super("Eliminar Carrito", true, true, false, true);
        this.carritoController = carritoController;
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 10, 10));
        lblCodigo = new JLabel("Código:");
        txtCodigo = new JTextField();
        btnBuscar = new JButton("Buscar");

        lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        txtUsuario.setEditable(false);

        lblFecha = new JLabel("Fecha:");
        txtFecha = new JTextField();
        txtFecha.setEditable(false);

        panelSuperior.add(lblCodigo);
        panelSuperior.add(txtCodigo);
        panelSuperior.add(new JLabel()); // espacio
        panelSuperior.add(btnBuscar);
        panelSuperior.add(lblUsuario);
        panelSuperior.add(txtUsuario);
        panelSuperior.add(lblFecha);
        panelSuperior.add(txtFecha);

        // Panel central (tabla)
        modeloDetalles = new DefaultTableModel();
        tblItems = new JTable(modeloDetalles);
        JScrollPane scrollPane = new JScrollPane(tblItems);

        // Panel inferior (botón eliminar)
        btnEliminar = new JButton("Eliminar");
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnEliminar);

        // Añadir íconos si existen
        URL urlBuscar = getClass().getResource("/search.png");
        if (urlBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(urlBuscar));
        } else {
            System.err.println("⚠️ Imagen /search.png no encontrada.");
        }

        URL urlEliminar = getClass().getResource("/trash.webp");
        if (urlEliminar != null) {
            btnEliminar.setIcon(new ImageIcon(urlEliminar));
        } else {
            System.err.println("⚠️ Imagen /trash.webp no encontrada.");
        }

        // Agregar componentes
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setSize(600, 400);

        // Eventos
        btnBuscar.addActionListener(e -> buscarCarrito());
        btnEliminar.addActionListener(e -> eliminarCarrito());
    }

    private void buscarCarrito() {
        if (!txtCodigo.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                carritoActual = carritoController.buscarCarrito(codigo);
                if (carritoActual != null) {
                    txtUsuario.setText(carritoActual.getUsuario().getUsername());
                    txtFecha.setText(FormateadorUtils.formatearFecha(carritoActual.getFechaCreacion().getTime(), locale));
                    mostrarItemsCarrito(carritoActual);
                } else {
                    mostrarMensaje(mensajes.get("carrito.buscar.noencontrado"));
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje("Código inválido.");
            }
        }
    }

    private void eliminarCarrito() {
        if (carritoActual != null) {
            carritoController.eliminarCarrito(carritoActual.getCodigo());
            mostrarMensaje(mensajes.get("carrito.eliminar.exito"));
            modeloDetalles.setRowCount(0);
            txtCodigo.setText("");
            txtUsuario.setText("");
            txtFecha.setText("");
        }
    }

    public void actualizarTextos() {
        locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setTitle(mensajes.get("carrito.eliminar.titulo.app"));

        lblCodigo.setText(mensajes.get("global.codigo"));
        lblUsuario.setText(mensajes.get("global.usuario"));
        lblFecha.setText(mensajes.get("global.fecha"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnas);
    }

    public void mostrarItemsCarrito(Carrito carrito) {
        modeloDetalles.setRowCount(0);
        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modeloDetalles.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
