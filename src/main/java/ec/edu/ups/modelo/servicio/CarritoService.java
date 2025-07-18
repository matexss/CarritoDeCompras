package ec.edu.ups.modelo.servicio;

import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de un carrito de compras en memoria.
 * Permite agregar, eliminar productos, calcular totales y consultar el estado del carrito.
 * <p>
 * Esta interfaz debe ser implementada por clases que manejen la lógica temporal del carrito,
 * desacoplándola de la persistencia a largo plazo.
 * </p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public interface CarritoService {

    /**
     * Agrega un producto al carrito con la cantidad especificada.
     * Si el producto ya existe, se incrementa su cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    void agregarProducto(Producto producto, int cantidad);

    /**
     * Elimina un producto del carrito por su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    void eliminarProducto(int codigoProducto);

    /**
     * Elimina todos los productos del carrito, dejándolo vacío.
     */
    void vaciarCarrito();

    /**
     * Calcula el valor total del carrito sumando el precio por cantidad de cada ítem.
     *
     * @return Total en valor monetario del carrito.
     */
    double calcularTotal();

    /**
     * Devuelve la lista de todos los ítems (producto y cantidad) en el carrito.
     *
     * @return Lista de ítems en el carrito.
     */
    List<ItemCarrito> obtenerItems();

    /**
     * Verifica si el carrito se encuentra vacío.
     *
     * @return true si no hay productos en el carrito, false en caso contrario.
     */
    boolean estaVacio();
}
