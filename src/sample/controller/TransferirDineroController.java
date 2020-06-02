package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import sample.bsn.CuentaBsn;
import sample.bsn.exception.SaldoInsuficienteException;
import sample.dao.exception.LlaveNoExisteException;

import java.io.IOException;

public class TransferirDineroController {

    @FXML
    private JFXTextField txtDato,
                         txtIdCuenta;

    private CuentaBsn cuentaBsn = new CuentaBsn();

    private VistaPrincipalController principal;

    private String id;

    @FXML
    public void  initialize() {
        txtDato.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=8){
                return change;
            }
            return null;
        }));

        txtIdCuenta.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("([1-9][0-9]*)?")&& change.getControlNewText().length()<=8){
                return change;
            }
            return null;
        }));
    }

    public void btnConsignar_action(){
        String idIngresado = txtIdCuenta.getText().trim();
        String  dato = txtDato.getText().trim();
        boolean esValido= validarCampos(idIngresado,dato);

        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transferencia");
            alert.setHeaderText("Transferencia");
            alert.setContentText("Diligencie todos los datos");
            alert.showAndWait();
            return;
        }
        Long datoIngresado = Long.valueOf(dato);
        try {
            cuentaBsn.transferirDinero(id,idIngresado,datoIngresado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Consignacion");
            alert.setHeaderText("Resultado de la operacion");
            alert.setContentText("Consignacion exitosa");
            alert.showAndWait();
            txtDato.clear();
            txtIdCuenta.clear();
        }catch (LlaveNoExisteException lnee){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Consignacion de dinero");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(lnee.getMessage());
            alert.showAndWait();
            return;
        } catch (SaldoInsuficienteException sie) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Consignacion de dinero");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(sie.getMessage());
            alert.showAndWait();
            return;
        } catch (IOException e) {
            e.printStackTrace();
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
