package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz DAO para la entidad Producto.
 * Define las operaciones CRUD y de consulta que deben implementar las clases concretas.
 */
public interface ProductoDAO {

    /**
     * Crea o guarda un nuevo producto en el sistema.
     *
     * @param producto Producto a registrar.
     */
    void crear(Producto producto);

    /**
     * Busca un producto por su código identificador.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos cuyo nombre contenga el texto indicado.
     *
     * @param nombre Nombre o parte del nombre del producto.
     * @return Lista de productos encontrados.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza la información de un producto existente.
     *
     * @param producto Producto con los nuevos datos.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Devuelve una lista con todos los productos almacenados.
     *
     * @return Lista de productos.
     */
    List<Producto> listarTodos();

}