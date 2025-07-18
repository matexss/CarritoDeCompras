package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz DAO para la entidad Usuario.
 * Define las operaciones de autenticación y CRUD, además de consultas por nombre o rol.
 */
public interface UsuarioDAO {

    /**
     * Autentica un usuario verificando su username y contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado o null si las credenciales no coinciden.
     */
    Usuario autenticar(String username, String password);

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usuario Usuario a registrar.
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Usuario encontrado o null.
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     */
    void eliminar(String username);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario Usuario con información actualizada.
     */
    void actualizar(Usuario usuario);

    /**
     * Lista todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Lista todos los usuarios que tienen un rol determinado.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios con ese rol.
     */
    List<Usuario> listarPorRol(Rol rol);

    /**
     * Busca usuarios cuyo nombre completo contenga el texto indicado.
     *
     * @param nombre Texto a buscar en el nombre completo.
     * @return Lista de usuarios encontrados.
     */
    List<Usuario> buscarPorNombre(String nombre);
}
