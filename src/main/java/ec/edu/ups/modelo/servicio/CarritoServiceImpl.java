package ec.edu.ups.modelo.servicio;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.util.List;

public class CarritoServiceImpl implements CarritoService {

    private Carrito carritoTemporal = new Carrito();

    public void agregarProducto(Producto producto, int cantidad) {
        carritoTemporal.agregarProducto(producto, cantidad);
    }

    public void eliminarProducto(int codigoProducto) {
        carritoTemporal.eliminarProducto(codigoProducto);
    }

    public void vaciarCarrito() {
        carritoTemporal = new Carrito();
    }

    public List<ItemCarrito> obtenerItems() {
        return carritoTemporal.obtenerItems();
    }

    public double calcularTotal() {
        return carritoTemporal.calcularSubtotal() * 1.12;
    }

    public boolean estaVacio() {
        return carritoTemporal.obtenerItems().isEmpty();
    }

    public Carrito getCarritoTemporal() {
        return carritoTemporal;
    }
}
