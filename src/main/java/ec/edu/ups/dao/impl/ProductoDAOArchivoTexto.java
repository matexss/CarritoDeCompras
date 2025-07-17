package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {

    private final File archivo;

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

    @Override
    public void crear(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir producto en archivo: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return listarTodos().stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

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

    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        if (!archivo.exists()) return productos;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    int codigo = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error al leer productos desde archivo: " + e.getMessage());
        }

        return productos;
    }
}
