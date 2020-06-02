package sample.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.bsn.exception.SaldoInsuficienteException;

import java.io.IOException;

public class VistaPrincipalController {
    @FXML
    private BorderPane vistaPrincipal;
    private String id;

    public MenuBar menuBarPrincipal;

    @FXML
    public void initialize(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/pantalla-inicio.fxml"));
            AnchorPane inicio = loader.load();
            PantallaInicioController controller = loader.getController();
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(inicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuAgregarSaldo_action(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/agregar-dinero.fxml"));
            AnchorPane inicio = loader.load();
            AgregarDineroController controller = loader.getController();
            controller.transferMessage(this.id);
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(inicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void mnuRetirarSaldo_action(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/retirar-dinero.fxml"));
            AnchorPane inicio = loader.load();
            RetirarDineroController controller = loader.getController();
            controller.transferMessage(this.id);
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(inicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuRealizarTransferencia_action(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/transferir-dinero.fxml"));
            AnchorPane inicio = loader.load();
            TransferirDineroController controller = loader.getController();
            controller.transferMessage(this.id);
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(inicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mnuConsultarSaldo_action(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/saldo.fxml"));
            AnchorPane saldo= loader.load();
            SaldoController controller = loader.getController();
            controller.transferMessage(this.id);
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(saldo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void habilitarMenu(){
        this.menuBarPrincipal.setVisible(true);
    }

    public void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/formulario-registro.fxml"));
            AnchorPane abrirRegistro = loader.load();
            FormularioRegistroController controller = loader.getController();
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(abrirRegistro);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/pantalla-inicio.fxml"));
            AnchorPane pantallaInicio = loader.load();
            PantallaInicioController controller = loader.getController();
            controller.setPrincipal(this);
            this.vistaPrincipal.setCenter(pantallaInicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void habilitarVistaBienvenida(){
        try {
            AnchorPane bienvenida = FXMLLoader
                    .load(getClass().getResource("../view/vista-bienvenida.fxml"));
            this.vistaPrincipal.setCenter(bienvenida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setIdCuenta(String idIngresado) {
        this.id = idIngresado;
    }
}
