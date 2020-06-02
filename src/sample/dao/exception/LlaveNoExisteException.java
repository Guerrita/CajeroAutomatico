package sample.dao.exception;

public class LlaveNoExisteException extends Exception {
    public LlaveNoExisteException(String llave){
        super(String.format("No existe un registro con esa llave: %s", llave));
    }
}
