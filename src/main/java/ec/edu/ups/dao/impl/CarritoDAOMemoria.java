package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoDAOMemoria implements CarritoDAO {

    private final Map<Integer, Carrito> carritos = new HashMap<>();

    @Override
    public void guardar(Carrito carrito) {
        carritos.put(carrito.getCodigo(), carrito);
        System.out.println(">>> DAO: Carrito guardado con código " + carrito.getCodigo());

    }

    @Override
    public Carrito buscarPorCodigo(int codigoCarrito) {
        return carritos.get(codigoCarrito);
    }

    @Override
    public void eliminarPorCodigo(int codigoCarrito) {
        carritos.remove(codigoCarrito);
    }

    @Override
    public List<Carrito> listarCarritos() {
        return new ArrayList<>(carritos.values());
    }
}
