package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementación de CarritoDAO que almacena carritos en un archivo de texto plano
 * utilizando un formato estructurado tipo ficha (clave-valor).
 * Permite guardar, buscar, eliminar y listar carritos.
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final File archivo;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor que crea el archivo de texto si no existe.
     *
     * @param ruta Ruta del archivo de almacenamiento.
     */
    public CarritoDAOArchivoTexto(String ruta) {
        this.archivo = new File(ruta);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creando archivo de carritos: " + e.getMessage());
            }
        }
    }

    /**
     * Guarda un carrito en el archivo de texto en formato ficha.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void guardar(Carrito carrito) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write("CarritoID: " + carrito.getCodigo()); bw.newLine();
            bw.write("Usuario: " + carrito.getUsuario().getUsername()); bw.newLine();
            bw.write("Fecha: " + formatoFecha.format(carrito.getFechaCreacion().getTime())); bw.newLine();
            bw.write("Items:"); bw.newLine();
            for (ItemCarrito item : carrito.obtenerItems()) {
                Producto p = item.getProducto();
                bw.write(String.format("  - Codigo: %d | Nombre: %s | Precio: %.2f | Cantidad: %d",
                        p.getCodigo(), p.getNombre(), p.getPrecio(), item.getCantidad()));
                bw.newLine();
            }
            bw.write("Total: " + carrito.calcularTotal()); bw.newLine();
            bw.write("---"); bw.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo carrito: " + e.getMessage());
        }
    }

    /**
     * Busca un carrito por su código dentro del archivo.
     *
     * @param codigoBuscado Código del carrito a buscar.
     * @return Carrito encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigoBuscado) {
        List<Carrito> carritos = listarTodos();
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigoBuscado) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Elimina un carrito del archivo basado en su código.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminarPorCodigo(int codigo) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(c -> c.getCodigo() == codigo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Carrito carrito : carritos) {
                bw.write("CarritoID: " + carrito.getCodigo()); bw.newLine();
                bw.write("Usuario: " + carrito.getUsuario().getUsername()); bw.newLine();
                bw.write("Fecha: " + formatoFecha.format(carrito.getFechaCreacion().getTime())); bw.newLine();
                bw.write("Items:"); bw.newLine();
                for (ItemCarrito item : carrito.obtenerItems()) {
                    Producto p = item.getProducto();
                    bw.write(String.format("  - Codigo: %d | Nombre: %s | Precio: %.2f | Cantidad: %d",
                            p.getCodigo(), p.getNombre(), p.getPrecio(), item.getCantidad()));
                    bw.newLine();
                }
                bw.write("Total: " + carrito.calcularTotal()); bw.newLine();
                bw.write("---"); bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar carrito: " + e.getMessage());
        }
    }

    /**
     * Lista todos los carritos almacenados en el archivo de texto.
     *
     * @return Lista de objetos Carrito.
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Carrito carrito = null;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("CarritoID:")) {
                    carrito = new Carrito();
                    carrito.setCodigo(Integer.parseInt(linea.split(":")[1].trim()));
                } else if (linea.startsWith("Usuario:")) {
                    Usuario u = new Usuario();
                    u.setUsername(linea.split(":")[1].trim());
                    carrito.setUsuario(u);
                } else if (linea.startsWith("Fecha:")) {
                    GregorianCalendar fecha = new GregorianCalendar();
                    String fechaTexto = linea.substring("Fecha:".length()).trim();
                    fecha.setTime(formatoFecha.parse(fechaTexto));
                    carrito.setFechaCreacion(fecha);
                } else if (linea.startsWith("  - Codigo:")) {
                    String[] partes = linea.split("\\|");
                    int codigo = Integer.parseInt(partes[0].split(":")[1].trim());
                    String nombre = partes[1].split(":")[1].trim();
                    double precio = Double.parseDouble(partes[2].split(":")[1].trim());
                    int cantidad = Integer.parseInt(partes[3].split(":")[1].trim());
                    Producto p = new Producto(codigo, nombre, precio);
                    carrito.agregarProducto(p, cantidad);
                } else if (linea.startsWith("---")) {
                    carritos.add(carrito);
                    carrito = null;
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo carritos: " + e.getMessage());
        }
        return carritos;
    }
}
