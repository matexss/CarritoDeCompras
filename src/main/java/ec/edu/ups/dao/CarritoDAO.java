package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import java.util.List;

public interface CarritoDAO {
    void guardar(Carrito carrito);
    List<Carrito> listarTodos();
    Carrito buscarPorCodigo(int codigo);
    void eliminarPorCodigo(int codigo);
}
