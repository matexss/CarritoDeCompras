package ec.edu.ups.servicio;

import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio utilitario que proporciona un conjunto predefinido de preguntas de seguridad.
 * <p>Estas preguntas pueden ser usadas durante el registro de usuarios o para recuperar contraseñas.</p>
 *
 * <p>Incluye métodos para obtener todas las preguntas disponibles o un subconjunto aleatorio.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class PreguntaSeguridadService {

    /**
     * Lista estática con las preguntas de seguridad predefinidas.
     */
    private static final List<String> PREGUNTAS = List.of(
            "¿Cuál es el nombre de tu primer mascota?",
            "¿Cuál es el nombre de tu héroe favorito?",
            "¿Cuál fue tu primer colegio?",
            "¿Qué comida odias?",
            "¿Cuál es tu película favorita?",
            "¿Qué país sueñas visitar?",
            "¿Cuál es el nombre de tu mejor amigo de la infancia?",
            "¿Qué deporte practicabas en la escuela?",
            "¿Cuál es tu segundo nombre?",
            "¿Cómo se llama tu abuela materna?"
    );

    /**
     * Devuelve una lista de objetos {@link Pregunta} con todas las preguntas disponibles.
     *
     * @return Lista completa de preguntas con identificador único.
     */
    public static List<Pregunta> obtenerTodasLasPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        for (int i = 0; i < PREGUNTAS.size(); i++) {
            preguntas.add(new Pregunta(i + 1, PREGUNTAS.get(i)));
        }
        return preguntas;
    }

    /**
     * Devuelve una lista aleatoria de preguntas, con la cantidad especificada.
     * Si la cantidad solicitada supera el total disponible, se devuelven todas.
     *
     * @param cantidad Número de preguntas aleatorias a obtener.
     * @return Lista aleatoria de preguntas.
     */
    public static List<Pregunta> obtenerPreguntasAleatorias(int cantidad) {
        List<Pregunta> todas = obtenerTodasLasPreguntas();
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(cantidad, todas.size()));
    }
}
