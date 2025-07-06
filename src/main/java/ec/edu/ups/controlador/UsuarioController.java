package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.servicio.PreguntaSeguridadService;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private Usuario usuarioAutenticado;

    private UsuarioCrearView crearView;
    private UsuarioEliminarView eliminarView;
    private UsuarioModificarView modificarView;
    private UsuarioListarView listarView;
    private UsuarioModificarMisView modificarMisView;

    private MensajeInternacionalizacionHandler mensajes;
    private MenuPrincipalView menuPrincipal;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;

        this.loginView.getBtnLogin().addActionListener(e -> autenticar());

        this.loginView.getBtnRegistrarse().addActionListener(e -> mostrarVistaRegistro());
        this.loginView.getBtnRecuperarContrasenia().addActionListener(e -> mostrarVistaRecuperarContrasenia());
    }

    public void setInternacionalizacionYVistas(MensajeInternacionalizacionHandler mensajes, MenuPrincipalView menuPrincipal) {
        this.mensajes = mensajes;
        this.menuPrincipal = menuPrincipal;

        this.crearView = new UsuarioCrearView(mensajes);
        this.eliminarView = new UsuarioEliminarView(mensajes);
        this.modificarView = new UsuarioModificarView(mensajes);
        this.listarView = new UsuarioListarView(mensajes);
        this.modificarMisView = new UsuarioModificarMisView(mensajes);
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    private void autenticar() {
        String user = loginView.getTxtUsuario().getText();
        String pass = new String(loginView.getTxtContrasenia().getPassword());

        Usuario usuario = usuarioDAO.autenticar(user, pass);
        if (usuario != null) {
            this.usuarioAutenticado = usuario;
            loginView.dispose();
        } else {
            loginView.mostrarMensaje("Credenciales incorrectas.");
        }
    }

    // === Registro de nuevo usuario desde el Login ===
    private void mostrarVistaRegistro() {
        RegistroView registroView = new RegistroView(new PreguntaSeguridadService());
        registroView.setVisible(true);

        registroView.getBtnRegistrar().addActionListener(e -> {
            if (!registroView.validarCampos()) return;

            Usuario usuario = new Usuario();
            usuario.setUsername(registroView.getTxtUsername().getText());
            usuario.setPassword(new String(registroView.getTxtContrasenia().getPassword()));
            usuario.setRol(Rol.USUARIO); // Fijo a USUARIO

            usuarioDAO.crear(usuario);
            registroView.mostrarMensaje("Usuario registrado correctamente");
            registroView.dispose();
        });
    }

    // === Recuperación de contraseña desde el Login ===
    private void mostrarVistaRecuperarContrasenia() {
        String pregunta = "¿Cuál es tu película favorita?"; // 🔁 puedes elegir otra pregunta también
        RecuperarContraseniaView recuperarView = new RecuperarContraseniaView(pregunta);
        recuperarView.setVisible(true);

        recuperarView.getBtnVerificarRespuestas().addActionListener(e -> {
            String respuesta = recuperarView.getRespuesta();

            // Aquí deberías hacer la verificación contra base de datos real
            if (respuesta.equalsIgnoreCase("respuestaCorrecta")) {
                recuperarView.habilitarCambioContrasenia();
                recuperarView.mostrarMensaje("Respuesta correcta");
            } else {
                recuperarView.mostrarMensaje("Respuesta incorrecta");
            }
        });

        recuperarView.getBtnCambiarContrasenia().addActionListener(e -> {
            String nueva = recuperarView.getNuevaContrasenia();
            if (!nueva.isEmpty()) {
                // 🔁 Aquí deberías buscar al usuario y actualizarlo
                recuperarView.mostrarMensaje("Contraseña actualizada correctamente");
                recuperarView.dispose();
            }
        });
    }

    // === Métodos del menú principal para admin ===

    public void mostrarVistaCrearUsuario() {
        crearView.getBtnCrear().addActionListener(e -> {
            Usuario usuario = new Usuario();
            usuario.setUsername(crearView.getTxtUsuario().getText());
            usuario.setPassword(crearView.getTxtContraseña().getText());
            usuario.setRol((Rol) crearView.getCbxRoles().getSelectedItem());

            usuarioDAO.crear(usuario);
            crearView.mostrarMensaje(mensajes.get("mensaje.usuario.creado"));
            crearView.limpiarCampos();
        });
        mostrarVentana(crearView);
    }

    public void mostrarVistaEliminarUsuario() {
        eliminarView.getBtnEliminar().addActionListener(e -> {
            String user = eliminarView.getTxtUsuario().getText();
            usuarioDAO.eliminar(user);
            eliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.eliminado"));
            eliminarView.limpiarCampos();
        });
        mostrarVentana(eliminarView);
    }

    public void mostrarVistaModificarUsuario() {
        modificarView.getBtnBuscar().addActionListener(e -> {
            Usuario u = usuarioDAO.buscarPorUsername(modificarView.getTxtUsuario().getText());
            if (u != null) {
                modificarView.getTxtContraseña().setText(u.getPassword());
                modificarView.getCbxRoles().setSelectedItem(u.getRol());
                modificarView.getTxtUsuario().setEditable(false);
                modificarView.getBtnBuscar().setEnabled(false);
                modificarView.getTxtContraseña().setEnabled(true);
                modificarView.getCbxRoles().setEnabled(true);
                modificarView.getBtnModificar().setEnabled(true);
            } else {
                modificarView.mostrarMensaje(mensajes.get("mensaje.usuario.no.encontrado"));
            }
        });

        modificarView.getBtnModificar().addActionListener(e -> {
            Usuario usuario = new Usuario();
            usuario.setUsername(modificarView.getTxtUsuario().getText());
            usuario.setPassword(modificarView.getTxtContraseña().getText());
            usuario.setRol((Rol) modificarView.getCbxRoles().getSelectedItem());

            usuarioDAO.actualizar(usuario);
            modificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificado"));
            modificarView.limpiarCampos();
        });

        mostrarVentana(modificarView);
    }

    public void mostrarVistaListarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        listarView.mostrarUsuarios(usuarios);
        mostrarVentana(listarView);
    }

    // === Vista de actualización para usuario normal ===

    public void mostrarVistaActualizarUsuario() {
        modificarMisView.getTxtUsuario().setText(usuarioAutenticado.getUsername());
        modificarMisView.getBtnModificar().addActionListener(e -> {
            usuarioAutenticado.setPassword(modificarMisView.getTxtContraseña().getText());
            usuarioAutenticado.setUsername(modificarMisView.getTxtNuevoUser().getText());

            usuarioDAO.actualizar(usuarioAutenticado);
            modificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificado"));
            modificarMisView.limpiarCampos();
        });

        mostrarVentana(modificarMisView);
    }

    // === Utilidad para mostrar JInternalFrame ===

    private void mostrarVentana(JInternalFrame vista) {
        for (JInternalFrame frame : menuPrincipal.getjDesktopPane().getAllFrames()) {
            frame.dispose();
        }
        menuPrincipal.getjDesktopPane().add(vista);
        vista.setVisible(true);
        vista.toFront();
    }
}
