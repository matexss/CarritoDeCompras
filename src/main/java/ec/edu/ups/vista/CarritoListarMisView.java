package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista interna para listar los carritos propios del usuario autenticado.
 * Permite visualizar el código, la fecha y la cantidad de ítems de cada carrito.
 * También permite eliminar o modificar los carritos listados.
 * <p>
 * Esta vista se integra dentro de una interfaz MDI y soporta internacionalización.
 * </p>
 *
 * @author Mateo
 * @version 1.0
 */
public class CarritoListarMisView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final JButton btnModificar, btnEliminar;

    /**
     * Constructor de la vista que lista los carritos del usuario autenticado.
     *
     * @param cc  Controlador de carrito para acceder a la lógica del sistema.
     * @param msg Manejador de internacionalización.
     */
    public CarritoListarMisView(CarritoController cc, MensajeInternacionalizacionHandler msg) {
        super("", true, true, false, true);
        carritoController = cc;
        mensajes = msg;
        locale = msg.getLocale();

        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        JLabel titulo = new JLabel("Mis Carritos", SwingConstants.CENTER);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        setSize(600, 400);

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.setBackground(fondo);
        btnModificar = new JButton(IconUtil.cargarIcono("carrito-editar.png", 16, 16));
        btnEliminar = new JButton(IconUtil.cargarIcono("carrito-eliminar.png", 16, 16));
        south.add(btnModificar);
        south.add(btnEliminar);
        add(south, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminar());
        btnModificar.addActionListener(e -> modificar());

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos();
            }
        });

        actualizarTextos(mensajes);
    }

    /**
     * Carga los textos internacionalizados en los botones y cabeceras de tabla.
     * También actualiza el título de la ventana.
     *
     * @param mensajes Manejador de idioma actual.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("menu.carrito.listarMis"));
        btnModificar.setToolTipText(mensajes.get("menu.carrito.modificar"));
        btnEliminar.setToolTipText(mensajes.get("menu.carrito.eliminar"));

        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.cantidad")
        });

        cargarCarritos();
    }

    /**
     * Carga en la tabla todos los carritos del usuario autenticado.
     * Si no existen carritos, la tabla queda vacía.
     */
    public void cargarCarritos() {
        modelo.setRowCount(0);
        List<Carrito> lista = carritoController.listarMisCarritos();
        if (lista == null) return;

        for (Carrito c : lista) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFechaCreacion().getTime(), locale),
                    c.obtenerItems().size()
            });
        }
    }

    /**
     * Elimina el carrito seleccionado en la tabla, luego de confirmar con el usuario.
     * Muestra un mensaje de advertencia si no se ha seleccionado ningún carrito.
     */
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

    /**
     * Abre una nueva ventana para modificar el carrito seleccionado.
     * Si no hay selección, no se hace nada.
     */
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
