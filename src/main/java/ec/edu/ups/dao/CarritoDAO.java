package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;
import java.util.List;

public interface CarritoDAO {
    void agregarProducto(Producto producto, int cantidad);
    void eliminarProducto(int codigo);
    List<Producto> listarProductos();
    double calcularSubtotal();
    void vaciarCarrito();

    void guardar(Carrito carrito);
}
