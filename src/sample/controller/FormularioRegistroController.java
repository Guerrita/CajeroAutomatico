package sample.controller;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import sample.bsn.CuentaBsn;
import sample.bsn.exception.ObjetoYaExisteException;
import sample.model.Cuenta;
import java.util.ArrayList;
import java.util.List;


public class FormularioRegistroController {

    private final List<String> tiposTransaccion= crearListaTipoGenero();

    private CuentaBsn cuentaBsn = new CuentaBsn();

    @FXML
    private JFXTextField txtNombres,
                txtApellidos,
                txtIdentificacion,
                txtCelular,txtSaldo;
    @FXML
    private JFXPasswordField txtContrasena,
                             txtConfirmarContrasena;

    @FXML
    private JFXComboBox<String> cmbGenero;

    private VistaPrincipalController principal;

    public void initialize(){
        ObservableList<String> observableTiposGenero = FXCollections.observableList(tiposTransaccion);
        cmbGenero.setItems(observableTiposGenero);

        txtSaldo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=8){
                return change;
            }
            return null;
        }));

        txtIdentificacion.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=12){
                return change;
            }
            return null;
        }));

        txtCelular.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=10){
                return change;
            }
            return null;
        }));



    }

    public void btnRegistrarse_action(){
        String nombresIngresados = txtNombres.getText().trim();
        String apellidosIngresados = txtApellidos.getText().trim();
        String generoIngresados = cmbGenero.getValue();
        String idIngresado = txtIdentificacion.getText().trim();
        String celularIngresado = txtCelular.getText().trim();
        String contrasenaIngresado = txtContrasena.getText().trim();
        String confirmarContrasenaIngresado = txtConfirmarContrasena.getText().trim();
        String saldoInicialIngresado = txtSaldo.getText().trim();

        boolean esValido = validarCampos(nombresIngresados,apellidosIngresados,generoIngresados,idIngresado,celularIngresado,contrasenaIngresado,confirmarContrasenaIngresado,saldoInicialIngresado);

        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro administrador");
            alert.setHeaderText("Registro administrador");
            alert.setContentText("Diligencie todos los datos");
            alert.showAndWait();
            return;
        }

        if (contrasenaIngresado.equals(confirmarContrasenaIngresado)){
            Cuenta cuenta = new Cuenta(nombresIngresados,apellidosIngresados, generoIngresados, idIngresado,celularIngresado,contrasenaIngresado,Long.valueOf(saldoInicialIngresado));
            try {
                cuentaBsn.registrarCuenta(cuenta);
                this.principal.setIdCuenta(idIngresado);
                this.principal.habilitarMenu();
                this.principal.habilitarVistaBienvenida();
            }catch (ObjetoYaExisteException lde){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Registro de Cuenta");
                alertError.setHeaderText("Ocurrio un error");
                alertError.setContentText(lde.getMessage());
                alertError.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro administrador");
            alert.setHeaderText("Contraseña");
            alert.setContentText("Las contraseñas no son iguales");
            txtContrasena.requestFocus();
            alert.showAndWait();
            return;
        }

    }

    public void btnRegresar_action(){
        this.principal.abrirInicioSesion();
    }

    private List<String> crearListaTipoGenero() {
        List<String> tipoGenero= new ArrayList<>(3);
        tipoGenero.add("Masculino");
        tipoGenero.add("Femenino");
        tipoGenero.add("Otro");
        return tipoGenero;
    }

    private boolean validarCampos(String... campos){
        for (int i = 0; i <campos.length ; i++) {
            if (campos[i]==null || "".equals(campos[i])){
                return false;
            }
        }
        return true;
    }

    public void setPrincipal(VistaPrincipalController vistaPrincipalController){
        this.principal = vistaPrincipalController;
    }
}
