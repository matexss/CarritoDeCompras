package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ProductoDAO que gestiona productos almacenados en un archivo de texto plano CSV.
 * Cada producto se guarda en una línea del archivo en formato: codigo,nombre,precio.
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {

    private final File archivo;

    /**
     * Constructor que inicializa y crea el archivo si no existe.
     *
     * @param rutaArchivo Ruta del archivo de productos.
     */
    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear el archivo de productos: " + e.getMessage());
            }
        }
    }

    /**
     * Guarda un nuevo producto en el archivo.
     *
     * @param producto Producto a registrar.
     */
    @Override
    public void crear(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir producto en archivo: " + e.getMessage());
        }
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return listarTodos().stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca productos cuyo nombre contenga el texto especificado (sin distinción de mayúsculas/minúsculas).
     *
     * @param nombre Nombre o parte del nombre del producto.
     * @return Lista de productos encontrados.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    /**
     * Actualiza un producto en el archivo. Reescribe todo el archivo reemplazando el producto existente.
     *
     * @param productoActualizado Producto con los nuevos datos.
     */
    @Override
    public void actualizar(Producto productoActualizado) {
        List<Producto> productos = listarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Producto p : productos) {
                if (p.getCodigo() == productoActualizado.getCodigo()) {
                    writer.write(productoActualizado.getCodigo() + "," +
                            productoActualizado.getNombre() + "," +
                            productoActualizado.getPrecio());
                } else {
                    writer.write(p.getCodigo() + "," +
                            p.getNombre() + "," +
                            p.getPrecio());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto del archivo con base en su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Producto p : productos) {
                writer.write(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
        }
    }

    /**
     * Lista todos los productos almacenados en el archivo CSV.
     *
     * @return Lista de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        if (!archivo.exists()) return productos;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int lineaActual = 0;

            while ((linea = reader.readLine()) != null) {
                lineaActual++;
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length != 3) {
                    System.err.println("[ADVERTENCIA] Línea malformada en archivo de productos (línea " + lineaActual + "): " + linea);
                    continue;
                }

                try {
                    int codigo = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    double precio = Double.parseDouble(partes[2].trim());
                    productos.add(new Producto(codigo, nombre, precio));
                } catch (NumberFormatException e) {
                    System.err.println("[ERROR] Error de formato numérico en línea " + lineaActual + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer productos desde archivo: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Muestra la lista de productos en consola con formato amigable.
     */
    public void listarPresentacion() {
        List<Producto> productos = listarTodos();
        System.out.println("=== Lista de Productos ===");
        for (Producto p : productos) {
            System.out.println(formatearProducto(p));
        }
        System.out.println("---");
    }

    /**
     * Formatea un producto en texto legible para presentación.
     *
     * @param producto Producto a formatear.
     * @return Cadena con información del producto.
     */
    private String formatearProducto(Producto producto) {
        return "ProductoID: " + producto.getCodigo() +
                " | Nombre: " + producto.getNombre() +
                " | Precio: " + String.format("%.2f", producto.getPrecio());
    }
}