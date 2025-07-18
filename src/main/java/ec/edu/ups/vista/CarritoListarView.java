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
 * Vista interna para listar todos los carritos del sistema, incluyendo los de todos los usuarios.
 * Esta funcionalidad está pensada para el rol ADMINISTRADOR.
 * <p>
 * Muestra el código del carrito, fecha de creación, cantidad de ítems y el nombre del usuario que lo creó.
 * </p>
 *
 * @author Mateo
 * @version 1.0
 */
public class CarritoListarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private final JTable tabla;
    private final DefaultTableModel modelo;

    /**
     * Constructor que inicializa la vista general de carritos.
     *
     * @param cc  Controlador de carrito para gestionar la lógica.
     * @param msg Manejador de internacionalización.
     */
    public CarritoListarView(CarritoController cc, MensajeInternacionalizacionHandler msg) {
        super("", true, true, true, true);
        carritoController = cc;
        mensajes = msg;
        locale = msg.getLocale();

        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        JLabel titulo = new JLabel("Listado General de Carritos", SwingConstants.CENTER);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        setSize(700, 400);

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos();
            }
        });

        actualizarTextos(mensajes);
    }

    /**
     * Actualiza los textos de la interfaz usando el manejador de internacionalización.
     *
     * @param mensajes Manejador que contiene los textos traducidos según el idioma actual.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("menu.carrito.listar"));
        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.usuario")
        });
        cargarCarritos();
    }

    /**
     * Carga todos los carritos del sistema y los muestra en la tabla.
     * Esta operación la realiza usando el método {@code listarCarritosSinFiltro()} del controlador.
     * Si no hay datos, la tabla permanece vacía.
     */
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
