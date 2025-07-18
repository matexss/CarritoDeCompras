package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CarritoDAO que almacena los carritos en memoria.
 * Ideal para pruebas o almacenamiento temporal sin persistencia en archivos o bases de datos.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> lista = new ArrayList<>();
    private int secuencia = 1;

    /**
     * Guarda un carrito en la lista en memoria y asigna un código incremental.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void guardar(Carrito carrito) {
        carrito.setCodigo(secuencia++);
        lista.add(carrito);
    }

    /**
     * Lista todos los carritos almacenados en memoria.
     *
     * @return Lista de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(lista);
    }

    /**
     * Busca un carrito por su código en la lista.
     *
     * @param codigo Código del carrito.
     * @return Carrito encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : lista) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    /**
     * Elimina un carrito de la lista por su código.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminarPorCodigo(int codigo) {
        lista.removeIf(c -> c.getCodigo() == codigo);
    }
}
