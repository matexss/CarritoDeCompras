package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final File archivo;
    private final List<Carrito> carritos = new ArrayList<>();

    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                for (Object c : (List<?>) obj) {
                    if (c instanceof Carrito) carritos.add((Carrito) c);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando carritos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.err.println("Error guardando carritos: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream().filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
    }

    @Override
    public void eliminarPorCodigo(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }
}
