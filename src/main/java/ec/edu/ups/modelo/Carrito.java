package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double IVA = 0.12;
    private static int contador = 1;

    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    public Carrito() {
        codigo = contador++;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public GregorianCalendar getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(GregorianCalendar fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        return calcularSubtotal() * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    public void actualizarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                break;
            }
        }
    }

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
