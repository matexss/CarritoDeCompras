package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de ProductoDAO que almacena los productos en memoria.
 * Ideal para pruebas unitarias o almacenamiento temporal sin persistencia real.
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista de productos.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
    }

    /**
     * Agrega un nuevo producto a la lista en memoria.
     *
     * @param producto Producto a registrar.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos por nombre exacto (ignorando mayúsculas/minúsculas).
     *
     * @param nombre Nombre del producto.
     * @return Lista de productos coincidentes.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista.
     *
     * @param producto Producto actualizado.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }

    /**
     * Elimina un producto de la lista según su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista completa de productos almacenados en memoria.
     *
     * @return Lista de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}
