package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class CarritoListarMisView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final JButton btnModificar, btnEliminar;

    public CarritoListarMisView(CarritoController cc, MensajeInternacionalizacionHandler msg) {
        super("", true, true, false, true);
        carritoController = cc;
        mensajes = msg;
        locale = msg.getLocale();

        setSize(600, 400);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel south = new JPanel();
        btnModificar = new JButton(IconUtil.cargarIcono("carrito-editar.png", 16, 16));
        btnEliminar  = new JButton(IconUtil.cargarIcono("carrito-eliminar.png", 16, 16));
        south.add(btnModificar);
        south.add(btnEliminar);
        add(south, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminar());
        btnModificar.addActionListener(e -> modificar());

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos();
            }
        });

        actualizarTextos();
    }

    @Override
    public void actualizarTextos() {
        setTitle(mensajes.get("menu.carrito.listarMis"));
        btnModificar.setToolTipText(mensajes.get("menu.carrito.modificar"));
        btnEliminar .setToolTipText(mensajes.get("menu.carrito.eliminar"));

        modelo.setColumnIdentifiers(new String[] {
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.cantidad")
        });
        cargarCarritos();
    }

    public void cargarCarritos() {
        modelo.setRowCount(0);
        List<Carrito> lista = carritoController.listarMisCarritos();
        if (lista == null) return;

        for (Carrito c : lista) {
            modelo.addRow(new Object[] {
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale),
                    c.obtenerItems().size()
            });
        }
    }

    private void eliminar() {
        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    mensajes.get("carrito.eliminar.seleccionar"),
                    mensajes.get("global.error"),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cod = (int) modelo.getValueAt(row, 0);

        int opcion = JOptionPane.showConfirmDialog(this,
                mensajes.get("carrito.eliminar.confirmacion") + " " + cod + "?",
                mensajes.get("menu.carrito.eliminar"),
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            carritoController.eliminarCarrito(cod);
            cargarCarritos();
        }
    }

    private void modificar() {
        int row = tabla.getSelectedRow();
        if (row == -1) return;
        int cod = (int) modelo.getValueAt(row, 0);

        CarritoModificarView v = new CarritoModificarView(carritoController, mensajes);
        v.buscarCarritoDesdeExterno(cod);

        getParent().add(v);
        v.setVisible(true);
        v.toFront();
    }
}
