package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Representa un ítem dentro del carrito de compras, compuesto por un producto
 * y la cantidad seleccionada del mismo. Permite calcular el subtotal de este ítem.
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class ItemCarrito implements Serializable {

    private static final long serialVersionUID = 1L;

    private Producto producto;
    private int cantidad;

    /**
     * Constructor que inicializa el ítem con un producto y una cantidad determinada.
     *
     * @param producto Producto asociado al ítem.
     * @param cantidad Cantidad del producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este ítem.
     *
     * @return Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad del producto en este ítem.
     *
     * @return Cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece una nueva cantidad para el producto en este ítem.
     *
     * @param cantidad Nueva cantidad a asignar.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Calcula el subtotal del ítem, es decir, el precio total por la cantidad.
     *
     * @return Subtotal calculado.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
