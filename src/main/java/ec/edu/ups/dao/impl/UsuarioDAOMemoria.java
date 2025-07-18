package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de UsuarioDAO que almacena usuarios en memoria.
 * Utilizado para pruebas o funcionamiento temporal sin persistencia en disco.
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    /**
     * Constructor que inicializa la lista de usuarios con dos cuentas por defecto: administrador y cliente.
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();

        Usuario admin = new Usuario();
        admin.setUsername("0150268688");
        admin.setPassword("Matemore_993");
        admin.setRol(Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Admin");
        admin.setFechaNacimiento("1990-01-01");
        admin.setCorreo("admin@correo.com");
        admin.setTelefono("000000");
        admin.setRespuestasSeguridad(new ArrayList<>());

        Usuario cliente = new Usuario();
        cliente.setUsername("cliente");
        cliente.setPassword("123");
        cliente.setRol(Rol.USUARIO);
        cliente.setNombreCompleto("Cliente");
        cliente.setFechaNacimiento("1995-01-01");
        cliente.setCorreo("cliente@correo.com");
        cliente.setTelefono("111111");
        cliente.setRespuestasSeguridad(new ArrayList<>());

        usuarios.add(admin);
        usuarios.add(cliente);
    }

    /**
     * Autentica a un usuario comparando username y contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado o null si no coincide.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista en memoria.
     *
     * @param usuario Usuario a registrar.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario por su username.
     *
     * @param username Nombre de usuario.
     * @return Usuario encontrado o null.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista por su username.
     *
     * @param username Nombre de usuario.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario Usuario actualizado.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    /**
     * Busca usuarios cuyo nombre de usuario contenga un texto dado.
     *
     * @param nombre Texto a buscar.
     * @return Lista de usuarios encontrados.
     */
    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    /**
     * Retorna la lista completa de usuarios almacenados en memoria.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Lista los usuarios que tienen un rol específico.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios con el rol indicado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }
}
