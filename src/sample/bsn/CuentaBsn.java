package sample.bsn;

import sample.bsn.exception.ObjetoYaExisteException;
import sample.bsn.exception.SaldoInsuficienteException;
import sample.dao.CuentaDao;
import sample.dao.exception.LlaveDuplicadaException;
import sample.dao.exception.LlaveNoExisteException;
import sample.dao.impl.CuentaDaoNio;
import sample.model.Cuenta;

import java.io.IOException;

public class CuentaBsn {
    private CuentaDao cuentaDao;

    public CuentaBsn(){
        cuentaDao = new CuentaDaoNio();
    }

    public void registrarCuenta(Cuenta cuenta) throws ObjetoYaExisteException {
        try {
            this.cuentaDao.registrarCuenta(cuenta);
        }catch (LlaveDuplicadaException lde){
            System.out.println(lde);
            throw new ObjetoYaExisteException(String.format("La cuenta con id: %s ya ha sido creada", cuenta.getId()));
        }
    }

    public void retirarDinero(String id,Long saldoCuenta) throws IOException, SaldoInsuficienteException, LlaveNoExisteException {
        Cuenta cuenta = this.cuentaDao.obtenerCuentaPorId(id);
        if (cuenta.getSaldoCuenta()-saldoCuenta<0){
            throw new SaldoInsuficienteException("El dinero que desea retirar es mas de lo que posee en la cuenta");
        }
        cuenta.setSaldoCuenta(cuenta.getSaldoCuenta()-saldoCuenta);
        cuentaDao.modificarSaldo(cuenta);

    }

    public void agregarDinero(String id,Long saldoCuenta) throws IOException, LlaveNoExisteException {
        Cuenta cuenta = this.cuentaDao.obtenerCuentaPorId(id);
        cuenta.setSaldoCuenta(cuenta.getSaldoCuenta() + saldoCuenta);
        cuentaDao.modificarSaldo(cuenta);
    }

    public void transferirDinero(String idEnvioDinero,String idRecepcionDinero, Long cantidad) throws LlaveNoExisteException, SaldoInsuficienteException, IOException {
        Cuenta cuentaEnvioDinero = this.cuentaDao.obtenerCuentaPorId(idEnvioDinero);
        if (cuentaEnvioDinero.getSaldoCuenta()-cantidad<0){
            throw new SaldoInsuficienteException("El dinero que desea retirar es mas de lo que posee en la cuenta");
        }
        cuentaEnvioDinero.setSaldoCuenta(cuentaEnvioDinero.getSaldoCuenta()-cantidad);
        try {
            Cuenta cuentaRecepcionDinero = this.cuentaDao.obtenerCuentaPorId(idRecepcionDinero);
            if (cuentaRecepcionDinero!=null){
                cuentaRecepcionDinero.setSaldoCuenta(cuentaRecepcionDinero.getSaldoCuenta() + cantidad);

                cuentaDao.modificarSaldo(cuentaEnvioDinero);
                cuentaDao.modificarSaldo(cuentaRecepcionDinero);
            }else {
                throw new LlaveNoExisteException(idRecepcionDinero);
            }

        }catch (LlaveNoExisteException lnee) {
            throw new LlaveNoExisteException(String.format("La cuenta con id: %s no existe", idRecepcionDinero));
        }
    }

    public Cuenta obtenerCuentaPorId(String id) throws LlaveNoExisteException {
        return cuentaDao.obtenerCuentaPorId(id);
    }
}
