package sample.dao.impl;

import sample.dao.CuentaDao;
import sample.dao.exception.LlaveDuplicadaException;
import sample.bsn.exception.SaldoInsuficienteException;
import sample.dao.exception.LlaveNoExisteException;
import sample.model.Cuenta;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;

public class CuentaDaoNio implements CuentaDao {


    private final static String NOMBRE_ARCHIVO="cuentas";
    private final static Path ARCHIVO= Paths.get(NOMBRE_ARCHIVO);
    private final static String FIELD_SEPARATOR = ",";
    private final static String RECORD_SEPARATOR = System.lineSeparator();

    public CuentaDaoNio(){
        if (!Files.exists(ARCHIVO)){
            try {
                Files.createFile(ARCHIVO);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void registrarCuenta(Cuenta cuenta) throws LlaveDuplicadaException {
        Optional<Cuenta> cuentaOptional = this.consultarCuentaPorId(cuenta.getId());
        if (cuentaOptional.isPresent()){
            throw new  LlaveDuplicadaException(cuenta.getId());
        }
        String cuentaString = parseCuenta2String(cuenta);
        byte[] datosRegistro = cuentaString.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try(FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)){
            fileChannel.write(byteBuffer);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseCuenta2String(Cuenta cuenta) {
        StringBuilder sb = new StringBuilder();
        sb.append(cuenta.getNombres()).append(FIELD_SEPARATOR)
                .append(cuenta.getApellidos()).append(FIELD_SEPARATOR)
                .append(cuenta.getGenero()).append(FIELD_SEPARATOR)
                .append(cuenta.getId()).append(FIELD_SEPARATOR)
                .append(cuenta.getCelular()).append(FIELD_SEPARATOR)
                .append(cuenta.getContrasena()).append(FIELD_SEPARATOR)
                .append(cuenta.getSaldoCuenta()).append(RECORD_SEPARATOR);
        return sb.toString();
    }
    @Override
    public Optional<Cuenta> consultarCuentaPorId(String id) {
        try (Stream<String> stream = Files.lines(ARCHIVO)) {
            Optional<String> cuentaString= stream.filter(cuenta -> id.equals(cuenta.split(FIELD_SEPARATOR)[3]))
                    .findFirst();
            if (cuentaString.isPresent()) {
                return Optional.of(parseCuenta2Object(cuentaString.get()));
            }
            return Optional.empty();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Cuenta obtenerCuentaPorId(String id) throws LlaveNoExisteException {
        try (Stream<String> stream = Files.lines(ARCHIVO)) {
            Optional<String> cuentaString= stream.filter(cuenta -> id.equals(cuenta.split(FIELD_SEPARATOR)[3]))
                    .findFirst();
            if (cuentaString.isPresent()) {
                return parseCuenta2Object(cuentaString.get());
            }
            return null;
        } catch (IOException ioe) {
            throw new LlaveNoExisteException(id);
        }
    }

    @Override
    public void modificarSaldo(Cuenta cuentaIngresada) throws IOException, LlaveNoExisteException {
        Scanner fileScanner = new Scanner(ARCHIVO);
        int lineNumber = 0;
        while(fileScanner.hasNextLine()) {
            if (cuentaIngresada.getId().equals(parseCuenta2Object(fileScanner.nextLine()).getId())) {
                break;
            } else {
                lineNumber++;
            }
        }
        Path path = Paths.get(NOMBRE_ARCHIVO);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber, parseCuenta2String(cuentaIngresada).replaceAll(RECORD_SEPARATOR,""));
        Files.write(path, lines, StandardCharsets.UTF_8);
    }


    private Cuenta parseCuenta2Object(String cuentaString) {
        String[] datoscuenta = cuentaString.split(FIELD_SEPARATOR);
        Cuenta cuenta = new Cuenta(datoscuenta[0],datoscuenta[1],datoscuenta[2],datoscuenta[3],datoscuenta[4],datoscuenta[5],Long.valueOf(datoscuenta[6]));
        return cuenta;
    }

    @Override
    public long saldo(String id) {
        return 0;
    }



}
