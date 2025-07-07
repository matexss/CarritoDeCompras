package ec.edu.ups.vista;

import ec.edu.ups.util.*;
import javax.swing.*;
import java.awt.*;

public class UsuarioActualizarView extends JFrame implements ActualizableConIdioma {
    private JPanel panel1;
    private JTextField txtNombre, txtFecha, txtCorreo, txtTelefono;
    private JPasswordField txtNueva;
    private JButton btnGuardar;
    private final MensajeInternacionalizacionHandler mensajes;

    private JLabel lNombre,lFecha,lCorreo,lTel,lNueva;

    public UsuarioActualizarView(MensajeInternacionalizacionHandler m){
        mensajes=m; init(); actualizarTextos();
    }
    private void init(){
        setSize(400,300); setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,5,5));
        lNombre=new JLabel(); txtNombre=new JTextField();
        lFecha=new JLabel();  txtFecha =new JTextField();
        lCorreo=new JLabel(); txtCorreo=new JTextField();
        lTel  =new JLabel();  txtTelefono=new JTextField();
        lNueva=new JLabel();  txtNueva =new JPasswordField();
        btnGuardar=new JButton(IconUtil.cargarIcono("guardar.png",18,18));

        add(lNombre); add(txtNombre);
        add(lFecha ); add(txtFecha);
        add(lCorreo); add(txtCorreo);
        add(lTel   ); add(txtTelefono);
        add(lNueva ); add(txtNueva);
        add(new JLabel()); add(btnGuardar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    @Override public void actualizarTextos(){
        setTitle(mensajes.get("usuario.actualizar.titulo"));
        lNombre.setText(mensajes.get("usuario.actualizar.nombre")+":");
        lFecha .setText(mensajes.get("usuario.actualizar.fecha.nacimiento")+":");
        lCorreo.setText(mensajes.get("usuario.actualizar.correo")+":");
        lTel.setText(mensajes.get("usuario.actualizar.telefono")+":");
        lNueva.setText(mensajes.get("usuario.actualizar.nueva.contrasenia")+":");
        btnGuardar.setText(mensajes.get("usuario.actualizar.btn.guardar"));
    }
}
