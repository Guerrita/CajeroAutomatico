package sample.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import sample.bsn.CuentaBsn;
import sample.dao.exception.LlaveNoExisteException;
import sample.model.Cuenta;


public class PantallaInicioController {

    @FXML
    private JFXTextField txtIdentificacion;
    @FXML
    private JFXPasswordField txtContrasena;

    public CuentaBsn cuentaBsn = new CuentaBsn();

    private VistaPrincipalController principal;

    public void initialize(){
        NumberValidator validadorNum = new NumberValidator();
        validadorNum.setMessage("El campo debe contener un dato numerico");
        FontIcon warnIconNum = new FontIcon(FontAwesomeSolid.SORT_NUMERIC_DOWN.getDescription());
        validadorNum.setIcon(warnIconNum);
        txtIdentificacion.getValidators().add(validadorNum);
        txtIdentificacion.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) txtIdentificacion.validate();
        });
        RequiredFieldValidator validator = new RequiredFieldValidator();
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.ARROW_UP.getDescription());
        validator.setIcon(warnIcon);

        txtContrasena.getValidators().add(validator);
        txtContrasena.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) txtContrasena.validate();
        });

        txtIdentificacion.getValidators().add(validator);
        txtIdentificacion.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) txtIdentificacion.validate();
        });
    }


    public void btnRegistrarse_action() {
        this.principal.abrirRegistro();
    }

    public void btnIniciarSesion_action() throws LlaveNoExisteException {
        String idIngresado = txtIdentificacion.getText().trim();
        String contrasenaIngresado = txtContrasena.getText().trim();
        boolean esValido = validarCampos(idIngresado,idIngresado);

        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inicio de sesion");
            alert.setHeaderText("Datos");
            alert.setContentText("Diligencie todos los datos");
            alert.showAndWait();
            return;
        }

        Cuenta cuenta = cuentaBsn.obtenerCuentaPorId(idIngresado);
            if (cuenta!=null) {
                if (idIngresado.equals(cuenta.getId()) && contrasenaIngresado.equals(cuenta.getContrasena())) {
                    this.principal.setIdCuenta(idIngresado);
                    this.principal.habilitarMenu();
                    this.principal.habilitarVistaBienvenida();
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ingreso a cuenta");
                    alert.setHeaderText("Contraseña");
                    alert.setContentText("La contraseña es incorrecta");
                    alert.showAndWait();
                    return;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ingreso a cuenta");
                alert.setHeaderText("Cuenta");
                alert.setContentText("La cuenta no existe o es incorrecta");
                alert.showAndWait();
                return;
            }
    }

    private boolean validarCampos(String... campos) {
        for (int i = 0; i <campos.length ; i++) {
            if (campos[i]==null || "".equals(campos[i])){
                return false;
            }
        }
        return true;
    }

    public void setPrincipal(VistaPrincipalController vistaPrincipalController) {
        this.principal = vistaPrincipalController;
    }
}
