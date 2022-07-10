package backend.Exception;

//Excepcion lanzada al apretar un boton sin tener una figura seleccionada
public class NothingSelectedException extends Exception {
    private final static String MESSAGE = "No hay ninguna figura seleccionada para ";

    public NothingSelectedException(String text) {
        super(MESSAGE + text);
    }
}