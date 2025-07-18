package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que representa a un usuario dentro del sistema.
 * <p>Contiene información personal, credenciales de acceso, rol asignado
 * y un conjunto de respuestas de seguridad para recuperación de cuenta.</p>
 *
 * <p>Implementa la interfaz {@link Serializable} para permitir su persistencia en archivos binarios o textos estructurados.</p>
 *
 * @author Mateo Morejon
 * @version 1.0
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre de usuario (también puede representar la cédula del usuario). */
    private String username;

    /** Contraseña del usuario, validada bajo criterios de seguridad. */
    private String password;

    /** Rol del usuario, que determina su nivel de acceso en el sistema. */
    private Rol rol;

    /** Lista de respuestas a preguntas de seguridad registradas por el usuario. */
    private List<RespuestaSeguridad> respuestasSeguridad;

    /** Nombre completo del usuario. */
    private String nombreCompleto;

    /** Fecha de nacimiento del usuario (formato yyyy-MM-dd). */
    private String fechaNacimiento;

    /** Dirección de correo electrónico del usuario. */
    private String correo;

    /** Número telefónico del usuario. */
    private String telefono;

    /** Constructor vacío necesario para serialización y frameworks. */
    public Usuario() {}

    // Métodos getters y setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<RespuestaSeguridad> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }

    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
