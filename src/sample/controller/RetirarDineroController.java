package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import sample.bsn.CuentaBsn;
import sample.bsn.exception.SaldoInsuficienteException;
import sample.dao.exception.LlaveNoExisteException;


import java.io.IOException;

public class RetirarDineroController {

    @FXML
    private JFXTextField txtDato;

    private CuentaBsn cuentaBsn = new CuentaBsn();

    private VistaPrincipalController principal;

    private String id;

    @FXML
    public void initialize() {
        txtDato.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?") && change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        }));
    }

    public void btnRetirar_action() {
        String valor = txtDato.getText().trim();
        boolean esValido= validarCampos(String.valueOf(valor));

        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Retiro");
            alert.setHeaderText("Retiro");
            alert.setContentText("Diligencie todos los datos");
            alert.showAndWait();
            return;
        }
        Long valorIngresado = Long.valueOf(txtDato.getText().trim());
        try {
            cuentaBsn.retirarDinero(id, valorIngresado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Retiro de dinero");
            alert.setHeaderText("Resultado de la operacion");
            alert.setContentText("Retiro exitoso");
            alert.showAndWait();
            txtDato.clear();
            return;
        }catch (IOException ioe){
            ioe.printStackTrace();
        } catch (SaldoInsuficienteException sie) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Retiro de dinero");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(sie.getMessage());
            alert.showAndWait();
            return;
        } catch (LlaveNoExisteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Retiro de dinero");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
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