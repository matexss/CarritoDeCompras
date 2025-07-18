package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoBinario implements UsuarioDAO, Serializable {

    private static final long serialVersionUID = 1L;
    private final File archivo;
    private final Map<String, Usuario> usuarios = new HashMap<>();

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
            admin.setRespuestasSeguridad(new ArrayList<>()); // vacío pero funcional

            usuarios.put(admin.getUsername(), admin); // ✔️ correcto
            guardarEnArchivo();
        }
    }

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

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al escribir archivo binario de usuarios: " + e.getMessage());
        }
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
        guardarEnArchivo();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.get(username);
    }

    @Override
    public void actualizar(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
        guardarEnArchivo();
    }

    @Override
    public void eliminar(String username) {
        usuarios.remove(username);
        guardarEnArchivo();
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public Usuario autenticar(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

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
