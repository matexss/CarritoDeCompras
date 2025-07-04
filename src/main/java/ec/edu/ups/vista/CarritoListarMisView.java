package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarritoListarMisView extends JInternalFrame {

    private CarritoController carritoController;
    private JTable tablaCarritos;
    private DefaultTableModel modeloTabla;
    private JPanel panelPrincipal;


    public CarritoListarMisView(CarritoController carritoController) {
        this.carritoController = carritoController;
        initComponents();
    }

    private void initComponents() {
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

        cargarCarritos();
    }

    private void cargarCarritos() {
        modeloTabla.setRowCount(0);
        List<Carrito> carritos = carritoController.listarMisCarritos();
        if (carritos != null) {
            for (Carrito c : carritos) {
                Object[] fila = {
                        c.getCodigo(),
                        c.getFechaCreacion().getTime(),
                        c.obtenerItems().size()
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}
