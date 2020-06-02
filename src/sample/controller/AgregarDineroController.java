package sample.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import sample.bsn.CuentaBsn;
import sample.dao.exception.LlaveNoExisteException;
import sample.model.Cuenta;

import java.io.IOException;

public class AgregarDineroController {

    @FXML
    private JFXTextField txtDato;

    private String id;

    private VistaPrincipalController principal;

    private CuentaBsn cuentaBsn = new CuentaBsn();

    @FXML
    public void  initialize() {
        txtDato.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=8){
                return change;
            }
            return null;
        }));
    }

    public void btnAgregar_action() throws IOException, LlaveNoExisteException {
        String dato= txtDato.getText().trim();
        boolean esValido= validarCampos(String.valueOf(dato));
        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Consignacion");
            alert.setHeaderText("Consignacion");
            alert.setContentText("Diligencie todos los datos");
            alert.showAndWait();
            return;
        }
        Long datoIngresado = Long.valueOf(txtDato.getText().trim());
        cuentaBsn.agregarDinero(id,datoIngresado);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consignacion");
        alert.setHeaderText("Resultado de la operacion");
        alert.setContentText("Consignacion exitosa");
        alert.showAndWait();
        txtDato.clear();

    }

    public void transferMessage(String id) {
        this.id = id;
    }

    public void setPrincipal(VistaPrincipalController vistaPrincipalController) {
        this.principal = vistaPrincipalController;
    }

    private boolean validarCampos(String... campos){
        for (int i = 0; i <campos.length ; i++) {
            if (campos[i]==null || "".equals(campos[i])){
                return false;
            }
        }
        return true;
    }
}
