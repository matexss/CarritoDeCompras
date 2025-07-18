package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.*;

/**
 * Implementación de UsuarioDAO que almacena usuarios en un archivo binario.
 * Utiliza un Map con username como clave para acceso eficiente.
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO, Serializable {

    private static final long serialVersionUID = 1L;
    private final File archivo;
    private final Map<String, Usuario> usuarios = new HashMap<>();

    /**
     * Constructor que carga los usuarios desde el archivo binario.
     * Si no existen usuarios, crea uno por defecto con rol ADMINISTRADOR.
     *
     * @param rutaArchivo Ruta del archivo binario.
     */
    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargarDesdeArchivo();

        if (usuarios.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setNombreCompleto("Administrador del Sistema");
            admin.setCorreo("admin@ups.edu.ec");
            admin.setTelefono("0999999999");
            admin.setFechaNacimiento("2000-01-01");
            admin.setRespuestasSeguridad(new ArrayList<>());

            usuarios.put(admin.getUsername(), admin);
            guardarEnArchivo();
        }
    }

    /**
     * Carga los usuarios desde el archivo binario.
     */
    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?> map) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if (entry.getKey() instanceof String && entry.getValue() instanceof Usuario) {
                        usuarios.put((String) entry.getKey(), (Usuario) entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer archivo binario de usuarios: " + e.getMessage());
        }
    }

    /**
     * Guarda los usuarios actuales en el archivo binario.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al escribir archivo binario de usuarios: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo usuario o actualiza uno existente.
     *
     * @param usuario Usuario a guardar.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
        guardarEnArchivo();
    }

    /**
     * Busca un usuario por su nombre de usuario (username).
     *
     * @param username Nombre de usuario.
     * @return Usuario encontrado o null.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.get(username);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario Usuario actualizado.
     */
    @Override
    public void actualizar(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
        guardarEnArchivo();
    }

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     */
    @Override
    public void eliminar(String username) {
        usuarios.remove(username);
        guardarEnArchivo();
    }

    /**
     * Lista todos los usuarios almacenados.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    /**
     * Autentica un usuario con su username y contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado o null si las credenciales no coinciden.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    /**
     * Lista usuarios que tengan el rol especificado.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios con el rol indicado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getRol().equals(rol)) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    /**
     * Busca usuarios por coincidencia parcial en su nombre completo.
     *
     * @param nombre Nombre o parte del nombre.
     * @return Lista de usuarios encontrados.
     */
    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }
}