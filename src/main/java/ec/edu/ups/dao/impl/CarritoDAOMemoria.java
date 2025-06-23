package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoDAOMemoria implements CarritoDAO {

    private final Map<Integer, Producto> carrito;
    private final Map<Integer, Integer> cantidades;

    public CarritoDAOMemoria() {
        carrito = new HashMap<>();
        cantidades = new HashMap<>();
    }

    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        carrito.put(producto.getCodigo(), producto);
        cantidades.put(producto.getCodigo(), cantidad);
    }

    @Override
    public void eliminarProducto(int codigo) {
        carrito.remove(codigo);
        cantidades.remove(codigo);
    }

    @Override
    public List<Producto> listarProductos() {
        return new ArrayList<>(carrito.values());
    }

    @Override
    public double calcularSubtotal() {
        double subtotal = 0;
        for (Producto producto : carrito.values()) {
            int cantidad = cantidades.get(producto.getCodigo());
            subtotal += producto.getPrecio() * cantidad;
        }
        return subtotal;
    }

    @Override
    public void vaciarCarrito() {
        carrito.clear();
        cantidades.clear();
    }
}
