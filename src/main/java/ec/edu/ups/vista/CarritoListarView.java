package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class CarritoListarView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private final JTable tabla;
    private final DefaultTableModel modelo;

    public CarritoListarView(CarritoController cc, MensajeInternacionalizacionHandler msg) {
        super("", true, true, true, true);
        carritoController = cc;
        mensajes = msg;
        locale   = msg.getLocale();

        setSize(700, 400);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        tabla  = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos();
            }
        });

        actualizarTextos();
    }

    @Override
    public void actualizarTextos() {
        setTitle(mensajes.get("menu.carrito.listar"));
        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.usuario")
        });
        cargarCarritos();
    }

    public void cargarCarritos() {
        modelo.setRowCount(0);
        List<Carrito> lista = carritoController.listarCarritosSinFiltro();
        if (lista == null) return;

        for (Carrito c : lista) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale),
                    c.obtenerItems().size(),
                    c.getUsuario() != null ? c.getUsuario().getUsername() : "N/A"
            });
        }
    }
}
