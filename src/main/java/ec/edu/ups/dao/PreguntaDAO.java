package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;
import java.util.List;

/**
 * Interfaz DAO para la entidad Pregunta de seguridad.
 * Define el contrato para listar todas las preguntas disponibles.
 */
public interface PreguntaDAO {

    /**
     * Devuelve una lista de todas las preguntas de seguridad almacenadas.
     *
     * @return Lista de preguntas.
     */
    List<Pregunta> listarTodas();

}