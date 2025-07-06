package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();

        // Usuario administrador
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setRol(Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Admin");
        admin.setFechaNacimiento("1990-01-01");
        admin.setCorreo("admin@correo.com");
        admin.setTelefono("000000");
        admin.setRespuestasSeguridad(new ArrayList<>());

        // Usuario cliente
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

    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

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

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

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


    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

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
