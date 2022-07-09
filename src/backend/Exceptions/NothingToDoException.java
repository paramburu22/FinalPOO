package backend.Exceptions;


public class NothingToDoException extends Exception {
    private final static String MESSAGE = "No hay acciones para ";
    public NothingToDoException(String text) {
        super(MESSAGE + text);
    }
}
