package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ProductoDAO que utiliza un archivo binario para almacenar los productos.
 * Cada operación de acceso actualiza el archivo correspondiente.
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {

    private final File archivo;

    /**
     * Constructor que inicializa el archivo binario con la ruta especificada.
     *
     * @param rutaArchivo Ruta del archivo binario donde se guardarán los productos.
     */
    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
    }

    /**
     * Carga todos los productos desde el archivo binario.
     *
     * @return Lista de productos cargados.
     */
    @SuppressWarnings("unchecked")
    private List<Producto> cargarDesdeArchivo() {
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Producto>) obj;
            }
        } catch (Exception e) {
            System.err.println("Error cargando productos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Guarda la lista de productos en el archivo binario.
     *
     * @param productos Lista de productos a guardar.
     */
    private void guardarEnArchivo(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error guardando productos: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo producto al archivo binario.
     *
     * @param producto Producto a agregar.
     */
    @Override
    public void crear(Producto producto) {
        List<Producto> productos = cargarDesdeArchivo();
        productos.add(producto);
        guardarEnArchivo(productos);
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return cargarDesdeArchivo().stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca productos que coincidan parcialmente con el nombre indicado.
     *
     * @param nombre Nombre o parte del nombre a buscar.
     * @return Lista de productos coincidentes.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : cargarDesdeArchivo()) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    /**
     * Actualiza un producto existente. Elimina el anterior y guarda el nuevo.
     *
     * @param producto Producto actualizado.
     */
    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = cargarDesdeArchivo();
        productos.removeIf(p -> p.getCodigo() == producto.getCodigo());
        productos.add(producto);
        guardarEnArchivo(productos);
    }

    /**
     * Elimina un producto por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = cargarDesdeArchivo();
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarEnArchivo(productos);
    }

    /**
     * Lista todos los productos almacenados en el archivo binario.
     *
     * @return Lista de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return cargarDesdeArchivo();
    }
}