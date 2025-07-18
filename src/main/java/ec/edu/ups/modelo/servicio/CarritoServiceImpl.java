package ec.edu.ups.modelo.servicio;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Implementación concreta del servicio de carrito que gestiona un carrito temporal en memoria.
 * Esta clase se encarga de manejar las operaciones comunes como agregar productos, eliminar,
 * vaciar el carrito, calcular totales y consultar el contenido del mismo.
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class CarritoServiceImpl implements CarritoService {

    /**
     * Carrito temporal mantenido en memoria durante la sesión del usuario.
     */
    private Carrito carritoTemporal = new Carrito();

    /**
     * Agrega un producto al carrito con la cantidad especificada.
     * Si el producto ya está presente, incrementa su cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        carritoTemporal.agregarProducto(producto, cantidad);
    }

    /**
     * Elimina un producto del carrito según su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    @Override
    public void eliminarProducto(int codigoProducto) {
        carritoTemporal.eliminarProducto(codigoProducto);
    }

    /**
     * Vacía completamente el carrito, eliminando todos los productos.
     */
    @Override
    public void vaciarCarrito() {
        carritoTemporal = new Carrito();
    }

    /**
     * Devuelve la lista de ítems actuales en el carrito.
     *
     * @return Lista de objetos {@link ItemCarrito}.
     */
    @Override
    public List<ItemCarrito> obtenerItems() {
        return carritoTemporal.obtenerItems();
    }

    /**
     * Calcula el total del carrito con un recargo del 12% (IVA).
     *
     * @return Total monetario con impuestos incluidos.
     */
    @Override
    public double calcularTotal() {
        return carritoTemporal.calcularSubtotal() * 1.12;
    }

    /**
     * Verifica si el carrito se encuentra vacío.
     *
     * @return true si no hay productos, false si hay al menos uno.
     */
    @Override
    public boolean estaVacio() {
        return carritoTemporal.obtenerItems().isEmpty();
    }

    /**
     * Devuelve el objeto interno del carrito temporal.
     * Útil para operaciones adicionales fuera de la interfaz.
     *
     * @return Carrito en uso.
     */
    public Carrito getCarritoTemporal() {
        return carritoTemporal;
    }
}
