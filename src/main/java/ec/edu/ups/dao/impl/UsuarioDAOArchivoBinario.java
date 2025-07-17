package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoBinario implements UsuarioDAO, Serializable {

    private final File archivo;
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?>) {
                Map<?, ?> mapaLeido = (Map<?, ?>) obj;
                for (Map.Entry<?, ?> entry : mapaLeido.entrySet()) {
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
            System.err.println("Error al guardar usuarios en binario: " + e.getMessage());
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
        crear(usuario);
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
        Usuario u = usuarios.get(username);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> lista = new ArrayList<>();
        for (Usuario u : usuarios.values()) {
            if (u.getRol().equals(rol)) lista.add(u);
        }
        return lista;
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> lista = new ArrayList<>();
        for (Usuario u : usuarios.values()) {
            if (u.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())) lista.add(u);
        }
        return lista;
    }
}
