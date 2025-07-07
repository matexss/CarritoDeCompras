package ec.edu.ups.modelo;

import java.util.List;

public class Usuario {

    private String username;
    private String password;
    private Rol rol;
    private List<String> preguntasSeguridad;
    private List<String> respuestasSeguridad;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String correo;
    private String telefono;

    public Usuario() {
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<String> getPreguntasSeguridad() { return preguntasSeguridad; }
    public void setPreguntasSeguridad(List<String> preguntasSeguridad) { this.preguntasSeguridad = preguntasSeguridad; }

    public List<String> getRespuestasSeguridad() { return respuestasSeguridad; }
    public void setRespuestasSeguridad(List<String> respuestasSeguridad) { this.respuestasSeguridad = respuestasSeguridad; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
