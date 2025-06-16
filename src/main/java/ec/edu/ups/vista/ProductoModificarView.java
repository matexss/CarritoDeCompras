package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoModificarView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton buscarButton;
    private JLabel lblCodigo;
    private JTextField txtModificar;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JComboBox cbxOpciones;
    private JLabel lblMensaje;
    private JButton btnModificar;

    public ProductoModificarView() {
        setContentPane(panelPrincipal);
        setTitle("Modificar Producto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public void setBuscarButton(JButton buscarButton) {
        this.buscarButton = buscarButton;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JTextField getTxtModificar() {
        return txtModificar;
    }

    public void setTxtModificar(JTextField txtModificar) {
        this.txtModificar = txtModificar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JComboBox getCbxOpciones() {
        return cbxOpciones;
    }

    public void setCbxOpciones(JComboBox cbxOpciones) {
        this.cbxOpciones = cbxOpciones;
    }

    public JLabel getLblMensaje() {
        return lblMensaje;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public void setLblMensaje(JLabel lblMensaje) {
        this.lblMensaje = lblMensaje;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
