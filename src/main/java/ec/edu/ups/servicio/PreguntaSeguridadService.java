package ec.edu.ups.servicio;

import ec.edu.ups.modelo.Pregunta;

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

    public static List<Pregunta> obtenerTodasLasPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        for (int i = 0; i < PREGUNTAS.size(); i++) {
            preguntas.add(new Pregunta(i + 1, PREGUNTAS.get(i)));
        }
        return preguntas;
    }

    public static List<Pregunta> obtenerPreguntasAleatorias(int cantidad) {
        List<Pregunta> todas = obtenerTodasLasPreguntas();
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(cantidad, todas.size()));
    }
}
