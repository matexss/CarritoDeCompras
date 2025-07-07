package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.util.ArrayList;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> lista = new ArrayList<>();
    private int secuencia = 1;

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuencia++);
        lista.add(carrito);
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : lista) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void eliminarPorCodigo(int codigo) {
        lista.removeIf(c -> c.getCodigo() == codigo);
    }
}
