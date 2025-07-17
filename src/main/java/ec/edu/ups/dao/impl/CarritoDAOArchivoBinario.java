package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final File archivo;
    private final List<Carrito> carritos;

    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                carritos.clear();
                carritos.addAll((List<Carrito>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer archivo binario de carritos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(carritos);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar archivo binario de carritos: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarPorCodigo(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos); // retorno copia para no exponer lista interna
    }
}
