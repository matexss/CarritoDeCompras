package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoBinario implements ProductoDAO {

    private final File archivo;
    private final List<Producto> productos = new ArrayList<>();

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                for (Object p : (List<?>) obj) {
                    if (p instanceof Producto) productos.add((Producto) p);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando productos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error guardando productos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarEnArchivo();
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream().filter(p -> p.getCodigo() == codigo).findFirst().orElse(null);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) encontrados.add(p);
        }
        return encontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        eliminar(producto.getCodigo());
        crear(producto);
    }

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarEnArchivo();
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
