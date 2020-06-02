package sample.dao;

import sample.dao.exception.LlaveDuplicadaException;
import sample.bsn.exception.SaldoInsuficienteException;
import sample.dao.exception.LlaveNoExisteException;
import sample.model.Cuenta;

import java.io.IOException;
import java.util.Optional;

public interface CuentaDao {
    void registrarCuenta(Cuenta cuenta) throws LlaveDuplicadaException;

    long saldo(String id);

    Optional<Cuenta> consultarCuentaPorId(String id);

    Cuenta obtenerCuentaPorId(String id) throws LlaveNoExisteException;

    void modificarSaldo(Cuenta cuenta) throws IOException, LlaveNoExisteException;
}
