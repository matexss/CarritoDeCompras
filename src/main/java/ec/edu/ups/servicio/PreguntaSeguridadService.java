package ec.edu.ups.servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreguntaSeguridadService {

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

    public static List<String> obtenerPreguntasAleatorias(int cantidad) {
        List<String> copia = new ArrayList<>(PREGUNTAS);
        Collections.shuffle(copia);
        return copia.subList(0, Math.min(cantidad, copia.size()));
    }

    public static List<String> obtenerTodasLasPreguntas() {
        return new ArrayList<>(PREGUNTAS);
    }
}
