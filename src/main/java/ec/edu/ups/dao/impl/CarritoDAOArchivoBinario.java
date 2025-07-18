package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz CarritoDAO que permite almacenar y recuperar objetos
 * Carrito desde un archivo binario. Se utiliza para persistencia a largo plazo.
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final File archivo;
    private final List<Carrito> carritos;

    /**
     * Constructor que inicializa el DAO con la ruta del archivo binario.
     *
     * @param rutaArchivo Ruta del archivo donde se almacenan los carritos.
     */
    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga los carritos desde el archivo binario si existe.
     * Maneja errores de entrada/salida y de clase no encontrada.
     */
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

    /**
     * Guarda la lista actual de carritos en el archivo binario.
     * Maneja errores de entrada/salida.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(carritos);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar archivo binario de carritos: " + e.getMessage());
        }
    }

    /**
     * Guarda un carrito en la lista y persiste el cambio en el archivo.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void guardar(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigo Código del carrito.
     * @return El carrito encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina un carrito de la lista según su código y actualiza el archivo.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminarPorCodigo(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Lista todos los carritos almacenados.
     *
     * @return Lista de carritos (copia de la lista interna).
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }
}
