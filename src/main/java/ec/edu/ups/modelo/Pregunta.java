package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa una pregunta de seguridad utilizada para la verificación de identidad del usuario.
 * Cada pregunta tiene un identificador único y un texto descriptivo.
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String texto;

    /**
     * Constructor que inicializa una pregunta con su identificador y texto.
     *
     * @param id Identificador único de la pregunta.
     * @param texto Contenido textual de la pregunta.
     */
    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /**
     * Devuelve el identificador de la pregunta.
     *
     * @return ID de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la pregunta.
     *
     * @param id Nuevo ID a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el texto de la pregunta.
     *
     * @return Texto de la pregunta.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto de la pregunta.
     *
     * @param texto Nuevo texto de la pregunta.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve una representación textual de la pregunta.
     *
     * @return Texto de la pregunta.
     */
    @Override
    public String toString() {
        return texto;
    }

    /**
     * Compara si dos objetos Pregunta son iguales basándose en su ID.
     *
     * @param o Objeto a comparar.
     * @return true si tienen el mismo ID; false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    /**
     * Devuelve el código hash de la pregunta, basado en su ID.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
