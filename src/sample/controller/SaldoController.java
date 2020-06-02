package sample.controller;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.bsn.CuentaBsn;
import sample.dao.exception.LlaveNoExisteException;
import sample.model.Cuenta;


public class SaldoController {

    @FXML
    private Label lblSaldo;


    private VistaPrincipalController principal;

    private String id;

    private CuentaBsn cuentaBsn = new CuentaBsn();

    public void btnConsultar_action() throws LlaveNoExisteException {
        Cuenta cuenta = cuentaBsn.obtenerCuentaPorId(id);
        lblSaldo.setText(String.valueOf(cuenta.getSaldoCuenta()));
    }



    public void setPrincipal(VistaPrincipalController vistaPrincipalController) {
        this.principal=vistaPrincipalController;
    }


    public void transferMessage(String id) {
        this.id=id;
    }
}
