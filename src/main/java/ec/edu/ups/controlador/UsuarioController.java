package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.servicio.PreguntaSeguridadService;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;
import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.ContraseniaInvalidaException;
import ec.edu.ups.util.ValidadorUsuario;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, MensajeInternacionalizacionHandler mensajes) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.mensajes = mensajes;

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

        listarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorNombre());
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

            // 🌐 Actualizar idioma global al iniciar sesión
            Locale localeSeleccionado = loginView.obtenerLocaleSeleccionado();
            mensajes.cambiarIdioma(localeSeleccionado.getLanguage(), localeSeleccionado.getCountry());

            loginView.dispose();
        } else {
            loginView.mostrarMensaje(mensajes.get("login.error"));
        }
    }


    private void mostrarVistaRegistro() {
        List<Pregunta> listaPreguntas = PreguntaSeguridadService.obtenerTodasLasPreguntas();

        RegistroView registroView = new RegistroView(mensajes, listaPreguntas);
        registroView.setVisible(true);

        registroView.getBtnRegistrar().addActionListener(e -> {
            if (!registroView.validarCampos()) return;

            Usuario usuario = new Usuario();
            usuario.setUsername(registroView.getTxtUsername().getText().trim());
            usuario.setPassword(new String(registroView.getTxtContrasenia().getPassword()));
            usuario.setRol(Rol.USUARIO);
            usuario.setNombreCompleto(registroView.getNombreCompleto());
            usuario.setFechaNacimiento(registroView.getFechaNacimiento());
            usuario.setCorreo(registroView.getCorreo());
            usuario.setTelefono(registroView.getTelefono());

            try {
                ValidadorUsuario.validarCedula(usuario.getUsername());
                ValidadorUsuario.validarContrasenia(usuario.getPassword());
                ValidadorUsuario.validarCorreo(usuario.getCorreo());
                ValidadorUsuario.validarTelefono(usuario.getTelefono());
            } catch (CedulaInvalidaException | ContraseniaInvalidaException ex) {
                registroView.mostrarMensaje(ex.getMessage());
                return;
            } catch (IllegalArgumentException ex) {
                registroView.mostrarMensaje("Error en campos: " + ex.getMessage());
                return;
            }

            // Procesar preguntas y respuestas
            List<Pregunta> preguntasSeleccionadas = registroView.getPreguntasSeleccionadas();
            List<String> respuestasSeleccionadas = registroView.getRespuestasSeleccionadas();

            List<RespuestaSeguridad> respuestas = new ArrayList<>();
            for (int i = 0; i < preguntasSeleccionadas.size(); i++) {
                respuestas.add(new RespuestaSeguridad(preguntasSeleccionadas.get(i), respuestasSeleccionadas.get(i)));
            }

            usuario.setRespuestasSeguridad(respuestas);
            usuarioDAO.crear(usuario);
            registroView.mostrarMensaje(mensajes.get("mensaje.usuario.creado"));
            registroView.dispose();
        });
    }





    private void mostrarVistaRecuperarContrasenia() {
        String username = JOptionPane.showInputDialog(null, mensajes.get("recuperar.usuario"));
        if (username == null || username.trim().isEmpty()) return;

        Usuario usuario = usuarioDAO.buscarPorUsername(username.trim());
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, mensajes.get("mensaje.usuario.no.encontrado"));
            return;
        }

        List<RespuestaSeguridad> lista = usuario.getRespuestasSeguridad();
        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensajes.get("recuperar.error.sin.preguntas"));
            return;
        }

        int indice = (int) (Math.random() * lista.size());
        RespuestaSeguridad rs = lista.get(indice);
        String pregunta = rs.getPregunta().getTexto();
        String respuestaCorrecta = rs.getRespuesta();

        RecuperarContraseniaView recuperarView = new RecuperarContraseniaView(mensajes, pregunta);
        recuperarView.setVisible(true);

        recuperarView.getBtnVerificarRespuestas().addActionListener(e -> {
            String respuesta = recuperarView.getRespuesta();
            if (respuesta.equalsIgnoreCase(respuestaCorrecta)) {
                recuperarView.habilitarCambioContrasenia();
                recuperarView.mostrarMensaje(mensajes.get("recuperar.msg.ok"));
            } else {
                recuperarView.mostrarMensaje(mensajes.get("recuperar.msg.fail"));
            }
        });

        recuperarView.getBtnCambiarContrasenia().addActionListener(e -> {
            String nueva = recuperarView.getNuevaContrasenia();
            if (!nueva.isEmpty()) {
                usuario.setPassword(nueva);
                usuarioDAO.actualizar(usuario);
                recuperarView.mostrarMensaje(mensajes.get("recuperar.msg.actualizada"));
                recuperarView.dispose();
            }
        });
    }

    public void mostrarVistaCrearUsuario() {
        crearView.getBtnCrear().addActionListener(e -> {
            String username = crearView.getTxtUsuario().getText().trim();
            String password = crearView.getTxtContraseña().getText().trim();
            Rol rol = (Rol) crearView.getCbxRoles().getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || rol == null) {
                crearView.mostrarMensaje(mensajes.get("mensaje.usuario.campos.vacios"));
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                crearView.mostrarMensaje(mensajes.get("mensaje.usuario.yaExiste"));
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setRol(rol);

            usuarioDAO.crear(usuario);
            crearView.mostrarMensaje(mensajes.get("mensaje.usuario.creado"));
            crearView.limpiarCampos();
        });
        mostrarVentana(crearView);
    }

    private boolean esAdmin() {
        return usuarioAutenticado != null && Rol.ADMINISTRADOR.equals(usuarioAutenticado.getRol());
    }

    public void mostrarVistaEliminarUsuario() {
        eliminarView.getBtnEliminar().addActionListener(e -> {
            String user = eliminarView.getTxtUsuario().getText();
            if (user.isBlank()) {
                eliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    eliminarView,
                    mensajes.get("usuario.eliminar.confirmacion") + ": " + user + "?",
                    mensajes.get("usuario.eliminar.titulo.app"),
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                usuarioDAO.eliminar(user);
                eliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.eliminado"));
                eliminarView.limpiarCampos();
            }
        });

        mostrarVentana(eliminarView);
    }

    public void mostrarVistaModificarUsuario() {
        modificarView.getBtnBuscar().addActionListener(e -> {
            if (!esAdmin()) {
                JOptionPane.showMessageDialog(menuPrincipal,
                        mensajes.get("permiso.denegado"),
                        mensajes.get("permiso.titulo"),
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

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

    public void buscarUsuarioPorNombre() {
        String nombre = listarView.getTxtUsuario().getText().trim();
        if (nombre.isEmpty()) {
            listarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
            return;
        }
        List<Usuario> usuarios = usuarioDAO.buscarPorNombre(nombre);
        listarView.mostrarUsuarios(usuarios);
    }

    private void mostrarVentana(JInternalFrame vista) {
        JDesktopPane desktop = menuPrincipal.getjDesktopPane();

        for (JInternalFrame frame : desktop.getAllFrames()) {
            frame.dispose();
            desktop.remove(frame);
        }

        if (vista.getParent() == null) {
            desktop.add(vista);
        }

        vista.setVisible(true);
        vista.toFront();
        try {
            vista.setSelected(true);
        } catch (java.beans.PropertyVetoException ignored) {}
    }
}
