package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Representa una respuesta de seguridad asociada a una pregunta específica.
 * Esta clase es utilizada para la recuperación de contraseñas u otras validaciones
 * que requieren preguntas personales.
 * <p>Implementa {@link Serializable} para permitir su persistencia en archivos.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class RespuestaSeguridad implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pregunta pregunta;
    private String respuesta;

    /**
     * Crea una instancia de RespuestaSeguridad con la pregunta y respuesta dadas.
     *
     * @param pregunta Pregunta de seguridad.
     * @param respuesta Respuesta proporcionada por el usuario.
     */
    public RespuestaSeguridad(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta asociada.
     *
     * @return La pregunta de seguridad.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * Establece la pregunta de seguridad.
     *
     * @param pregunta Pregunta a asociar.
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * Devuelve la respuesta registrada.
     *
     * @return Respuesta del usuario.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece una nueva respuesta.
     *
     * @param respuesta Texto de la respuesta.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Verifica si la respuesta dada coincide con la respuesta almacenada (ignorando mayúsculas y espacios).
     *
     * @param respuestaAValidar Respuesta que se desea validar.
     * @return true si la respuesta es correcta, false en caso contrario.
     */
    public boolean esRespuestaCorrecta(String respuestaAValidar) {
        if (this.respuesta == null || respuestaAValidar == null) {
            return false;
        }
        return this.respuesta.trim().equalsIgnoreCase(respuestaAValidar.trim());
    }

    /**
     * Representación en texto de la respuesta de seguridad.
     *
     * @return Cadena con información de la pregunta y la respuesta.
     */
    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
