package backend.Action;

import backend.CanvasState;

public enum ActionType {
    DRAW("Dibujar "){
        @Override
        public void undo(CanvasState canvasState){
            canvasState.deleteLastFigure();
        }
    },DELETE("Borrar "){
        @Override
        public void undo(CanvasState canvasState){

        }
    },LINECOLOR("Cambiar color de borde de "){
        @Override
        public void undo(CanvasState canvasState){

        }
    }
    ,FILLCOLOR("Cambiar color de relleno de "){
        @Override
        public void undo(CanvasState canvasState){

        }
    },INCREASE("Agrandar "){
        @Override
        public void undo(CanvasState canvasState){

        }
    },DECREASE("Achicar "){
        @Override
        public void undo(CanvasState canvasState){

        }
    };

    private final String message;

    ActionType(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public abstract void undo(CanvasState canvasState);


}
