package backend.Action;


public enum ActionType {
    DRAW("Dibujar"),DELETE("Borrar"),LINECOLOR("Cambiar color de borde de")
    ,FILLCOLOR("Cambiar color de relleno de"),INCREASE("Agrandar"),DECREASE("Achicar");

    private final String message;

    ActionType(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
