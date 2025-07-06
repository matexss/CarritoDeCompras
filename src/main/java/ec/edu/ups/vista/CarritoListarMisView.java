package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarritoListarMisView extends JInternalFrame {
    private JPanel panelPrincipal;
    private final CarritoController carritoController;
    private JTable tablaCarritos;
    private DefaultTableModel modeloTabla;

    public CarritoListarMisView(CarritoController carritoController) {
        this.carritoController = carritoController;
        initComponents();

        // ✅ Se actualiza la tabla cada vez que se abre la ventana
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCarritos();
            }
        });
    }

    private void initComponents() {
        JPanel panelBotones = new JPanel();
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        setTitle("Mis Carritos");
        setSize(600, 400);
        setClosable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"Código", "Fecha Creación", "Cantidad Productos"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCarritos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaCarritos);
        add(scrollPane, BorderLayout.CENTER);
        cargarCarritos();           // ← carga inicial

    }

    public void cargarCarritos() {
        modeloTabla.setRowCount(0);
        List<Carrito> carritos = carritoController.listarMisCarritos();

        if (carritos != null && !carritos.isEmpty()) {
            for (Carrito c : carritos) {
                modeloTabla.addRow(new Object[]{
                        c.getCodigo(),
                        c.getFechaCreacion().getTime(),
                        c.obtenerItems().size()
                });
            }
        } else {
            System.out.println(">>> No hay carritos del usuario autenticado.");
        }
}
}
