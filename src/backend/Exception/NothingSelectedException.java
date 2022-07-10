package backend.Exception;

//Excepcion lanzada al
public class NothingSelectedException extends Exception {
    private final static String MESSAGE = "No hay ninguna figura seleccionada para ";

    public NothingSelectedException(String text) {
        super(MESSAGE + text);
    }
}