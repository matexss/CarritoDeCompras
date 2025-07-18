package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Representa un carrito de compras que contiene múltiples productos agrupados en ítems.
 * Cada carrito está asociado a un usuario, tiene una fecha de creación y permite
 * calcular totales, gestionar productos y mantener un estado temporal.
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;

    private final double IVA = 0.12;
    private static int contador = 1;

    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    /**
     * Constructor por defecto que inicializa el carrito con un código único,
     * fecha actual y lista vacía de ítems.
     */
    public Carrito() {
        codigo = contador++;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Agrega un producto al carrito con una cantidad específica.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina del carrito un producto según su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Elimina todos los productos del carrito.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Obtiene la lista de ítems actuales del carrito.
     *
     * @return Lista de {@link ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return true si no contiene ítems, false en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el subtotal del carrito sin incluir impuestos.
     *
     * @return Suma de los precios de los ítems por su cantidad.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /**
     * Calcula el valor del IVA sobre el subtotal.
     *
     * @return Valor del impuesto.
     */
    public double calcularIVA() {
        return calcularSubtotal() * IVA;
    }

    /**
     * Calcula el total a pagar incluyendo IVA.
     *
     * @return Total monetario del carrito.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Actualiza la cantidad de un producto específico dentro del carrito.
     *
     * @param codigoProducto Código del producto.
     * @param nuevaCantidad  Nueva cantidad a establecer.
     */
    public void actualizarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                break;
            }
        }
    }

    /**
     * Devuelve una representación textual del carrito, útil para depuración.
     *
     * @return Cadena con la información del carrito.
     */
    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", codigo=" + codigo +
                ", fechaCreacion=" + fechaCreacion +
                ", items=" + items +
                ", usuario=" + (usuario != null ? usuario.getUsername() : "null") +
                '}';
    }
}
