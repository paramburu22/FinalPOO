package backend.Exception;

//Excepcion lanzada al apretar rehacer o deshacer sin haber acciones para rehacer o deshacer respectivamente
public class NothingToDoException extends Exception {
    private final static String MESSAGE = "No hay acciones para ";
    public NothingToDoException(String text) {
        super(MESSAGE + text);
    }
}
