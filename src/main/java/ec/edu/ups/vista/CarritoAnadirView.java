package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class CarritoAnadirView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private JPanel panel1;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnGuardar;
    private JTable table;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private DefaultTableModel modelo;

    private JLabel lblCodigo, lblNombre, lblPrecio, lblCantidad;
    private JLabel lblSubtotal, lblIva, lblTotal;

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajes, CarritoController carritoController) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.carritoController = carritoController;
        this.locale = mensajes.getLocale();
        initComponents();
        initListeners();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        setSize(750, 550);
        setLayout(new BorderLayout());
        Color fondo = new Color(255, 228, 232);
        getContentPane().setBackground(fondo);

        JLabel titulo = new JLabel("Añadir Carrito", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 5, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panelBusqueda.setBackground(fondo);

        lblCodigo = new JLabel();
        lblNombre = new JLabel();
        lblPrecio = new JLabel();
        lblCantidad = new JLabel();

        txtCodigo = new JTextField();
        txtNombre = new JTextField(); txtNombre.setEditable(false);
        txtPrecio = new JTextField(); txtPrecio.setEditable(false);
        txtCantidad = new JTextField("1");

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnAñadir = new JButton(IconUtil.cargarIcono("carrito-añadir.png", 18, 18));
        btnGuardar = new JButton(IconUtil.cargarIcono("guardar.png", 18, 18));

        panelBusqueda.add(lblCodigo);
        panelBusqueda.add(txtCodigo);
        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        panelBusqueda.add(lblPrecio);
        panelBusqueda.add(txtPrecio);
        panelBusqueda.add(lblCantidad);
        panelBusqueda.add(txtCantidad);
        panelBusqueda.add(btnAñadir);

        add(panelBusqueda, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"#", "#", "#", "#", "#"}, 0);
        table = new JTable(modelo);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 10, 5));
        panelTotales.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelTotales.setBackground(fondo);

        lblSubtotal = new JLabel();
        lblIva = new JLabel();
        lblTotal = new JLabel();

        txtSubtotal = new JTextField(); txtSubtotal.setEditable(false);
        txtIva = new JTextField(); txtIva.setEditable(false);
        txtTotal = new JTextField(); txtTotal.setEditable(false);

        panelTotales.add(lblSubtotal);
        panelTotales.add(txtSubtotal);
        panelTotales.add(lblIva);
        panelTotales.add(txtIva);
        panelTotales.add(lblTotal);
        panelTotales.add(txtTotal);

        JPanel panelPie = new JPanel(new BorderLayout());
        panelPie.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPie.setBackground(fondo);
        panelPie.add(panelTotales, BorderLayout.CENTER);

        JPanel aux = new JPanel();
        aux.setBackground(fondo);
        aux.add(btnGuardar);
        panelPie.add(aux, BorderLayout.EAST);

        add(panelPie, BorderLayout.SOUTH);
    }

    private void initListeners() {
        btnBuscar.addActionListener(e -> {
            String codigoTxt = txtCodigo.getText().trim();
            if (!codigoTxt.matches("\\d+")) {
                mostrarMensaje(mensajes.get("carrito.msg.codigoInvalido"));
                return;
            }
            int codigo = Integer.parseInt(codigoTxt);
            Producto p = carritoController.buscarProductoPorCodigo(codigo);
            if (p == null) {
                mostrarMensaje(mensajes.get("carrito.msg.noEncontrado"));
                txtNombre.setText(""); txtPrecio.setText("");
            } else {
                txtNombre.setText(p.getNombre());
                txtPrecio.setText(String.valueOf(p.getPrecio()));
            }
        });

        btnAñadir.addActionListener(e -> {
            if (txtCodigo.getText().isBlank() ||
                    txtNombre.getText().isBlank() ||
                    txtPrecio.getText().isBlank() ||
                    txtCantidad.getText().isBlank()) {
                mostrarMensaje(mensajes.get("carrito.msg.camposVacios"));
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            Producto prod = new Producto(codigo, nombre, precio);
            carritoController.agregarProducto(prod, cantidad);

            boolean actualizada = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (Integer.parseInt(modelo.getValueAt(i, 0).toString()) == codigo) {
                    int cant = Integer.parseInt(modelo.getValueAt(i, 3).toString()) + cantidad;
                    modelo.setValueAt(cant, i, 3);
                    modelo.setValueAt(String.format("%.2f", cant * precio), i, 4);
                    actualizada = true;
                    break;
                }
            }
            if (!actualizada) {
                modelo.addRow(new Object[]{
                        codigo, nombre,
                        String.format("%.2f", precio),
                        cantidad,
                        String.format("%.2f", precio * cantidad)
                });
            }
            actualizarTotales();
            txtCodigo.setText(""); txtNombre.setText("");
            txtPrecio.setText(""); txtCantidad.setText("1");
        });

        btnGuardar.addActionListener(e -> {
            if (carritoController.estaVacio()) {
                mostrarMensaje(mensajes.get("carrito.msg.vacio"));
                return;
            }
            Carrito c = new Carrito();
            carritoController.guardarCarrito(c);
            mostrarMensaje(mensajes.get("carrito.msg.guardado"));

            modelo.setRowCount(0);
            txtSubtotal.setText(""); txtIva.setText(""); txtTotal.setText("");
        });
    }

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("carrito.titulo"));

        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblNombre.setText(mensajes.get("global.nombre") + ":");
        lblPrecio.setText(mensajes.get("global.precio") + ":");
        lblCantidad.setText(mensajes.get("global.cantidad") + ":");

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnAñadir.setText(mensajes.get("carrito.boton.anadir"));
        btnGuardar.setText(mensajes.get("carrito.boton.guardar"));

        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        });

        lblSubtotal.setText(mensajes.get("carrito.total.subtotal") + ":");
        lblIva.setText(mensajes.get("carrito.total.iva") + ":");
        lblTotal.setText(mensajes.get("carrito.total.total") + ":");

        actualizarTotales();
    }

    private void actualizarTotales() {
        double sub = carritoController.obtenerItems()
                .stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
        double iva = sub * 0.12;
        double tot = sub + iva;

        txtSubtotal.setText(FormateadorUtils.formatearMoneda(sub, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(tot, locale));
    }

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, mensajes.get("yesNo.app.titulo"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    public JButton getBtnAñadir() { return btnAñadir; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTable getTable1() { return table; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIva() { return txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
}
