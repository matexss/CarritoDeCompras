package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import java.util.List;

/**
 * Interfaz DAO para la entidad Carrito. Define las operaciones básicas
 * de persistencia que deben implementar las clases concretas.
 */
public interface CarritoDAO {

    /**
     * Guarda un carrito en el almacenamiento correspondiente.
     *
     * @param carrito Carrito a guardar.
     */
    void guardar(Carrito carrito);

    /**
     * Lista todos los carritos registrados.
     *
     * @return Lista de carritos.
     */
    List<Carrito> listarTodos();

    /**
     * Busca un carrito por su código identificador.
     *
     * @param codigo Código del carrito.
     * @return Carrito encontrado o null si no existe.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Elimina un carrito según su código.
     *
     * @param codigo Código del carrito a eliminar.
     */
    void eliminarPorCodigo(int codigo);
}