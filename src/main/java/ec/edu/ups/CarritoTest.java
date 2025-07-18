package ec.edu.ups;

import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.servicio.CarritoService;
import ec.edu.ups.modelo.servicio.CarritoServiceImpl;

/**
 * Clase de prueba para verificar el funcionamiento del servicio de carrito de compras.
 * Se crean productos, se añaden al carrito, se imprime su contenido,
 * se calcula el total y se realizan operaciones como eliminar y vaciar.
 *
 * <p>Este test permite validar las operaciones básicas del carrito temporal
 * usando la implementación {@link CarritoServiceImpl}.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class CarritoTest {

    /**
     * Método principal que ejecuta las pruebas sobre la funcionalidad del carrito.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // Crear servicio de carrito
        CarritoService carrito = new CarritoServiceImpl();

        // Crear productos
        Producto p1 = new Producto(1, "Mouse", 15.0);
        Producto p2 = new Producto(2, "Teclado", 25.0);

        // Agregar productos al carrito
        carrito.agregarProducto(p1, 2);  // 2 x $15 = $30
        carrito.agregarProducto(p2, 1);  // 1 x $25 = $25

        // Mostrar los ítems
        System.out.println("Contenido del carrito:");
        for (ItemCarrito item : carrito.obtenerItems()) {
            System.out.println("- " + item.getProducto().getNombre() +
                    " x" + item.getCantidad() +
                    " = $" + item.getSubtotal());
        }

        // Calcular total
        double total = carrito.calcularTotal();
        System.out.println("Total: $" + total);

        // Verificar si está vacío
        System.out.println("¿Carrito vacío? " + carrito.estaVacio());

        // Eliminar producto y vaciar carrito
        carrito.eliminarProducto(1);
        carrito.vaciarCarrito();

        System.out.println("Carrito vaciado. ¿Vacío ahora? " + carrito.estaVacio());
    }
}
