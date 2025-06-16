package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;
import ec.edu.ups.vista.ProductoModificarView;

import java.awt.event.*;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO, ProductoAnadirView productoView,
                              ProductoListaView productoListaView, ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        productoListaView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                mostrarProductos();
            }

        });
        productoEliminarView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                mostrarProductos();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(productoEliminarView.getTxtNombre().getText());
                Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
                if (productoEncontrado == null) {
                    productoEliminarView.mostrarMensaje("El producto no existe");
                }else {
                    productoEliminarView.mostrarMensaje("El producto " + productoEncontrado.getNombre()+" fue eliminado correctamente");
                    productoDAO.eliminar(codigo);
                    productoEliminarView.getTxtNombre().setText("");
                    productoEliminarView.mostrarProductos(productoDAO.listarTodos());
                }
            }
        });

        productoModificarView.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo  = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
                if (productoEncontrado == null) {
                    productoModificarView.mostrarMensaje("El producto no existe");
                }else {
                    productoModificarView.getLblCodigo().setText(String.valueOf(productoEncontrado.getCodigo()));
                    productoModificarView.getLblNombre().setText(productoEncontrado.getNombre());
                    productoModificarView.getLblPrecio().setText(String.valueOf(productoEncontrado.getPrecio()));
                    productoModificarView.getCbxOpciones().setEnabled(true);
                    productoModificarView.getTxtModificar().setEditable(true);
                    productoModificarView.getCbxOpciones().addItemListener(new ItemListener() {

                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            String tipo = String.valueOf(productoModificarView.getCbxOpciones().getSelectedItem());
                            switch (tipo) {
                                case "Modificar Nombre":
                                    productoModificarView.getLblMensaje().setText("Nuevo Nombre");
                                    break;
                                case "Modificar Codigo":
                                    productoModificarView.getLblMensaje().setText("Nuevo Codigo");
                                    break;
                                case "Modificar Precio":

                                    productoModificarView.getLblMensaje().setText("Nuevo Precio");
                                    break;
                                default:
                                    productoModificarView.mostrarMensaje("Ingrese el tipo de modificacion");
                            }
                        }
                    });
                    productoModificarView.getBtnModificar().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String tipo = String.valueOf(productoModificarView.getCbxOpciones().getSelectedItem());
                            Producto productonuevo = new Producto();
                            switch (tipo) {
                                case "Modificar Nombre":
                                    productoDAO.eliminar(codigo);
                                    productonuevo.setCodigo(productoEncontrado.getCodigo());
                                    productonuevo.setNombre(productoModificarView.getTxtModificar().getText());
                                    productonuevo.setPrecio(productoEncontrado.getPrecio());
                                    productoDAO.crear(productonuevo);
                                    break;
                                case "Modificar Codigo":
                                    productoDAO.eliminar(codigo);
                                    productonuevo.setCodigo(Integer.parseInt(productoModificarView.getTxtModificar().getText()));
                                    productonuevo.setNombre(productoEncontrado.getNombre());
                                    productonuevo.setPrecio(productoEncontrado.getPrecio());
                                    productoDAO.crear(productonuevo);
                                    break;
                                case "Modificar Precio":
                                    productoDAO.eliminar(codigo);
                                    productonuevo.setCodigo(productoEncontrado.getCodigo());
                                    productonuevo.setNombre(productoEncontrado.getNombre());
                                    productonuevo.setPrecio(Double.parseDouble(productoModificarView.getTxtModificar().getText()));
                                    productoDAO.crear(productonuevo);
                                    break;
                                default:
                                    productoModificarView.mostrarMensaje("Ingrese el tipo de modificacion");
                            }
                            productoModificarView.mostrarMensaje("Modificado correctamente"+productonuevo);
                            productoModificarView.getTxtModificar().setText("");
                            productoModificarView.getCbxOpciones().setEnabled(false);
                            productoModificarView.getLblCodigo().setText("");
                            productoModificarView.getLblNombre().setText("");
                            productoModificarView.getLblPrecio().setText("");
                            productoModificarView.getTxtCodigo().setText("");
                        }
                    });

                }
            }
        });


    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.getTxtBuscar().setText("");
        productoListaView.mostrarProductos(productos);
    }
    private void mostrarProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
        productoEliminarView.mostrarProductos(productoDAO.listarTodos());
    }
    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }
}