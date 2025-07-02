package ec.edu.ups.controlador;
import ec.edu.ups.servicio.PreguntaSeguridadService;
import ec.edu.ups.vista.RecuperarContraseniaView;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.RegistroView;

import javax.swing.*;
import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private Usuario usuarioAutenticado;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;

        // Acción del botón "Iniciar sesión"
        this.loginView.getBtnLogin().addActionListener(e -> autenticar());

        // Acción del botón "Registrarse"
        this.loginView.getBtnRegistrarse().addActionListener(e -> {
            PreguntaSeguridadService preguntaService = new PreguntaSeguridadService();
            RegistroView registroView = new RegistroView(preguntaService);

            registroView.getBtnRegistrar().addActionListener(ev -> {
                if (registroView.validarCampos()) {
                    String username = registroView.getTxtUsername().getText();
                    String password = new String(registroView.getTxtContrasenia().getPassword());
                    Rol rolSeleccionado = Rol.valueOf((String) registroView.getComboRol().getSelectedItem());

                    List<String> preguntas = registroView.getPreguntasSeguridad();
                    List<String> respuestas = registroView.getRespuestasSeguridad();

                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(username);
                    nuevoUsuario.setPassword(password);
                    nuevoUsuario.setRol(rolSeleccionado);
                    nuevoUsuario.setPreguntasSeguridad(preguntas);
                    nuevoUsuario.setRespuestasSeguridad(respuestas);

                    usuarioDAO.crear(nuevoUsuario);
                    registroView.mostrarMensaje("Usuario registrado exitosamente.");
                    registroView.dispose();
                }
            });

            registroView.setVisible(true);
        });


        // Acción del botón "Recuperar contraseña"
        this.loginView.getBtnRecuperarContrasenia().addActionListener(e -> {
            String username = loginView.getTxtUsuario().getText();
            Usuario usuario = usuarioDAO.buscarPorUsername(username);

            if (usuario != null) {
                List<String> preguntas = usuario.getPreguntasSeguridad();
                RecuperarContraseniaView recuperarView = new RecuperarContraseniaView(preguntas);

                recuperarView.getBtnVerificarRespuestas().addActionListener(ev -> {
                    String r1 = recuperarView.getTxtPregunta1().getText().trim();
                    String r2 = recuperarView.getTxtPregunta2().getText().trim();
                    String r3 = recuperarView.getTxtPregunta3().getText().trim();

                    List<String> respuestasCorrectas = usuario.getRespuestasSeguridad();

                    if (respuestasCorrectas.size() >= 3 &&
                            r1.equalsIgnoreCase(respuestasCorrectas.get(0)) &&
                            r2.equalsIgnoreCase(respuestasCorrectas.get(1)) &&
                            r3.equalsIgnoreCase(respuestasCorrectas.get(2))) {

                        recuperarView.mostrarMensaje("Respuestas correctas. Ahora puedes cambiar tu contraseña.");
                        recuperarView.getBtnCambiarContrasenia().setEnabled(true);
                    } else {
                        recuperarView.mostrarMensaje("Respuestas incorrectas. Inténtalo nuevamente.");
                    }
                });

                recuperarView.getBtnCambiarContrasenia().addActionListener(ev -> {
                    String nuevaContrasenia = new String(recuperarView.getTxtNuevaContrasenia().getPassword());
                    if (nuevaContrasenia.length() < 4) {
                        recuperarView.mostrarMensaje("La contraseña debe tener al menos 4 caracteres.");
                        return;
                    }

                    usuario.setPassword(nuevaContrasenia);
                    usuarioDAO.actualizar(usuario);
                    recuperarView.mostrarMensaje("Contraseña actualizada correctamente.");
                    recuperarView.dispose();
                });

                recuperarView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(loginView, "Usuario no encontrado.");
            }
        });
    }

    private void autenticar() {
        String username = loginView.getTxtUsuario().getText();
        String password = new String(loginView.getTxtContrasenia().getPassword());

        Usuario usuario = usuarioDAO.autenticar(username, password);
        if (usuario != null) {
            this.usuarioAutenticado = usuario;
            JOptionPane.showMessageDialog(loginView, "Inicio de sesión exitoso.");
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(loginView, "Credenciales incorrectas.");
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
