package ec.edu.ups.util;

/**
 * Interfaz funcional que debe ser implementada por aquellas clases
 * que deseen soportar actualización dinámica de textos con base en la internacionalización.
 * <p>
 * Esta interfaz es especialmente útil para vistas o componentes gráficos que
 * requieren actualizar sus etiquetas, títulos y mensajes al cambiar el idioma.
 * </p>
 *
 * <p><strong>Uso:</strong><br>
 * Implementar esta interfaz en cualquier clase Swing (como JInternalFrame o JPanel)
 * que deba reaccionar ante un cambio de idioma.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public interface ActualizableConIdioma {

    /**
     * Método que debe ser implementado para actualizar todos los textos visibles del componente,
     * utilizando el manejador de mensajes internacionalizados proporcionado.
     *
     * @param mensajes Instancia de {@link MensajeInternacionalizacionHandler} con los textos traducidos.
     */
    void actualizarTextos(MensajeInternacionalizacionHandler mensajes);
}
