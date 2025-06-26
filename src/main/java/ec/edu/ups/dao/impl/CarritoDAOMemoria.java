package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private Carrito carritoGuardado = new Carrito(); // para simular persistencia en memoria

    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        carritoGuardado.agregarProducto(producto, cantidad);
    }

    @Override
    public void eliminarProducto(int codigo) {
        carritoGuardado.eliminarProducto(codigo);
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        carritoGuardado.obtenerItems().forEach(item -> productos.add(item.getProducto()));
        return productos;
    }

    @Override
    public double calcularSubtotal() {
        return carritoGuardado.calcularSubtotal();
    }

    @Override
    public void vaciarCarrito() {
        carritoGuardado = new Carrito(); // reinicia el carrito
    }

    @Override
    public void guardar(Carrito carrito) {
        this.carritoGuardado = carrito; // simula guardar
    }
}
