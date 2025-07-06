package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarritoListarView extends JInternalFrame {

    private CarritoController carritoController;
    private JTable tablaCarritos;
    private DefaultTableModel modeloTabla;
    private JPanel panelPrincipal;

    public CarritoListarView(CarritoController carritoController) {
        super("Listado General de Carritos", true, true, true, true);
        this.carritoController = carritoController;
        initComponents();


        // ✅ Listener para recargar los datos al activar la ventana
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos(); // 🔄 Recarga datos cada vez que se abre
            }
        });
    }

    private void initComponents() {
        setSize(700, 400);
        setClosable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"Código", "Fecha Creación", "Cantidad Productos", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCarritos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCarritos);

        add(scrollPane, BorderLayout.CENTER);

        cargarCarritos(); // Carga inicial (opcional)
    }

    public void cargarCarritos() {
        modeloTabla.setRowCount(0);
        List<Carrito> carritos = carritoController.listarCarritosSinFiltro();
        System.out.println(">>> Carritos encontrados: " + (carritos != null ? carritos.size() : "null"));

        if (carritos != null) {
            for (Carrito c : carritos) {
                System.out.println("- Código: " + c.getCodigo() + " | Usuario: " +
                        (c.getUsuario() != null ? c.getUsuario().getUsername() : "null") +
                        " | Productos: " + c.obtenerItems().size());

                Object[] fila = {
                        c.getCodigo(),
                        c.getFechaCreacion().getTime(),
                        c.obtenerItems().size(),
                        (c.getUsuario() != null ? c.getUsuario().getUsername() : "N/A")
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}
