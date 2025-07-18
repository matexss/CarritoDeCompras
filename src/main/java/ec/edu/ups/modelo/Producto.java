package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase que representa un producto disponible para la compra.
 * Contiene atributos como código, nombre y precio del producto.
 * Implementa {@link Serializable} para permitir su persistencia.
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor vacío requerido para serialización y frameworks.
     */
    public Producto() {
    }

    /**
     * Constructor para inicializar un producto con todos sus atributos.
     *
     * @param codigo Código único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio unitario del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo Código identificador del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Precio unitario.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el código del producto.
     *
     * @return Código identificador.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el nombre del producto.
     *
     * @return Nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el precio del producto.
     *
     * @return Precio unitario.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Representación textual del producto para interfaces gráficas o listados.
     *
     * @return Cadena con nombre y precio del producto.
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }

}
