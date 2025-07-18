package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Enumeración que representa los distintos roles de usuario dentro del sistema.
 * <p>Se utiliza para controlar los permisos y accesos a funcionalidades
 * específicas en función del rol del usuario.</p>
 *
 * <ul>
 *     <li>{@code ADMINISTRADOR}: Usuario con privilegios completos sobre el sistema.</li>
 *     <li>{@code USUARIO}: Usuario estándar con permisos limitados.</li>
 * </ul>
 *
 * <p>Implementa {@link Serializable} para permitir su persistencia en archivos.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public enum Rol implements Serializable {

    /**
     * Rol que representa a un administrador del sistema.
     */
    ADMINISTRADOR,

    /**
     * Rol que representa a un usuario común del sistema.
     */
    USUARIO;

    private static final long serialVersionUID = 1L;
}
