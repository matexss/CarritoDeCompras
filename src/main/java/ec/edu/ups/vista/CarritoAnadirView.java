package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.IconUtil;

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
    private JButton   btnBuscar;
    private JButton   btnAñadir;
    private JButton   btnGuardar;
    private JTable    table;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private DefaultTableModel modelo;

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajes,
                             CarritoController carritoController) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.carritoController = carritoController;
        this.locale = mensajes.getLocale();
        initComponents();
        initListeners();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(750, 550);
        setLayout(new BorderLayout());

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 5, 10, 10));

        txtCodigo   = new JTextField();
        txtNombre   = new JTextField();  txtNombre.setEditable(false);
        txtPrecio   = new JTextField();  txtPrecio.setEditable(false);
        txtCantidad = new JTextField("1");

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnAñadir = new JButton(IconUtil.cargarIcono("carrito-añadir.png", 18, 18));
        btnGuardar = new JButton(IconUtil.cargarIcono("guardar.png", 18, 18));


        panelBusqueda.add(new JLabel());
        panelBusqueda.add(txtCodigo);
        panelBusqueda.add(new JLabel());
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        panelBusqueda.add(new JLabel());
        panelBusqueda.add(txtPrecio);
        panelBusqueda.add(new JLabel());
        panelBusqueda.add(txtCantidad);
        panelBusqueda.add(btnAñadir);

        add(panelBusqueda, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"#", "#", "#", "#", "#"}, 0);
        table  = new JTable(modelo);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 10, 5));
        txtSubtotal = new JTextField(); txtSubtotal.setEditable(false);
        txtIva      = new JTextField(); txtIva.setEditable(false);
        txtTotal    = new JTextField(); txtTotal.setEditable(false);

        panelTotales.add(new JLabel());
        panelTotales.add(txtSubtotal);
        panelTotales.add(new JLabel());
        panelTotales.add(txtIva);
        panelTotales.add(new JLabel());
        panelTotales.add(txtTotal);

        btnGuardar = new JButton(IconUtil.cargarIcono("guardar.png"));
        JPanel panelPie = new JPanel(new BorderLayout());
        panelPie.add(panelTotales, BorderLayout.CENTER);
        JPanel aux = new JPanel(); aux.add(btnGuardar);
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

            int codigo   = Integer.parseInt(txtCodigo.getText().trim());
            String nombre= txtNombre.getText().trim();
            double precio= Double.parseDouble(txtPrecio.getText().trim());
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
    public void actualizarTextos() {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("carrito.titulo"));

        JPanel p = (JPanel) getContentPane().getComponent(0);
        ((JLabel) p.getComponent(0)).setText(mensajes.get("global.codigo") + ":");
        ((JLabel) p.getComponent(2)).setText(mensajes.get("global.nombre") + ":");
        ((JLabel) p.getComponent(5)).setText(mensajes.get("global.precio") + ":");
        ((JLabel) p.getComponent(7)).setText(mensajes.get("global.cantidad") + ":");

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

        JPanel tot = (JPanel) ((JPanel) getContentPane().getComponent(2)).getComponent(0);
        ((JLabel) tot.getComponent(0)).setText(mensajes.get("carrito.total.subtotal") + ":");
        ((JLabel) tot.getComponent(2)).setText(mensajes.get("carrito.total.iva") + ":");
        ((JLabel) tot.getComponent(4)).setText(mensajes.get("carrito.total.total") + ":");

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