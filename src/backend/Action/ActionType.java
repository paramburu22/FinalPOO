package backend.Action;


public enum ActionType {
    DRAW("Dibujar"),DELETE("Borrar"),LINECOLOR("Cambiar color de borde ")
    ,FILLCOLOR("Cambiar color de relleno "),INCREASE("Agrandar"),DECREASE("Achicar");

    private final String message;

    ActionType(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
