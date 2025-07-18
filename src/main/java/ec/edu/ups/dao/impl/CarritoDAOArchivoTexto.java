        package ec.edu.ups.dao.impl;

        import ec.edu.ups.dao.CarritoDAO;
        import ec.edu.ups.modelo.Carrito;
        import ec.edu.ups.modelo.ItemCarrito;
        import ec.edu.ups.modelo.Producto;
        import ec.edu.ups.modelo.Usuario;
        import java.io.*;
        import java.text.SimpleDateFormat;
        import java.util.*;

        public class CarritoDAOArchivoTexto implements CarritoDAO {

            private final File archivo;
            private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

            @Override
            public void eliminarPorCodigo(int codigo) {
                List<Carrito> carritos = listarTodos(); // leer todos
                carritos.removeIf(c -> c.getCodigo() == codigo); // eliminar el que coincida

                // Reescribir el archivo desde cero
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
