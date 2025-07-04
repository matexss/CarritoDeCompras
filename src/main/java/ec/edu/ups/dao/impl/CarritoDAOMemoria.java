package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoDAOMemoria implements CarritoDAO {

    // Simula la base de datos de carritos guardados
    private final Map<Integer, Carrito> carritos = new HashMap<>();

    // Carrito en construcción (temporal hasta que se guarda)
    private Carrito carritoTemporal = new Carrito();

    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        carritoTemporal.agregarProducto(producto, cantidad);
    }

    @Override
    public void eliminarProducto(int codigoProducto) {
        carritoTemporal.eliminarProducto(codigoProducto);
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        carritoTemporal.obtenerItems().forEach(item -> productos.add(item.getProducto()));
        return productos;
    }

    @Override
    public double calcularSubtotal() {
        return carritoTemporal.calcularSubtotal();
    }

    @Override
    public void vaciarCarrito() {
        carritoTemporal = new Carrito(); // reinicia carrito temporal
    }

    @Override
    public void guardar(Carrito carrito) {
        carritos.put(carrito.getCodigo(), carrito);
        carritoTemporal = new Carrito(); // reinicia carrito temporal para siguiente uso
    }

    @Override
    public Carrito buscarPorCodigo(int codigoCarrito) {
        return carritos.get(codigoCarrito);
    }

    @Override
    public void eliminarPorCodigo(int codigoCarrito) {
        carritos.remove(codigoCarrito);
    }

    @Override
    public List<Carrito> listarCarritos() {
        return new ArrayList<>(carritos.values());
    }
}
