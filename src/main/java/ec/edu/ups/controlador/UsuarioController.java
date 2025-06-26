package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.RegistroView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistro();
            }
        });
    }

    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        } else {
            loginView.dispose();
        }
    }

    private void mostrarVentanaRegistro() {
        RegistroView registroView = new RegistroView();
        registroView.setVisible(true);

        registroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registroView.getTxtUsername().getText();
                String contrasenia = new String(registroView.getTxtContrasenia().getPassword());
                String rolSeleccionado = (String) registroView.getComboRol().getSelectedItem();

                if (username.isEmpty() || contrasenia.isEmpty()) {
                    registroView.mostrarMensaje("Por favor complete todos los campos.");
                    return;
                }

                if (usuarioDAO.buscarPorUsername(username) != null) {
                    registroView.mostrarMensaje("El usuario ya existe.");
                    return;
                }

                Usuario nuevo = new Usuario(username, contrasenia, Rol.valueOf(rolSeleccionado));
                usuarioDAO.crear(nuevo);
                registroView.mostrarMensaje("Usuario registrado con éxito.");
                registroView.dispose();
            }
        });
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }
}
