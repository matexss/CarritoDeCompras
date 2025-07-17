package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String texto;

    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
