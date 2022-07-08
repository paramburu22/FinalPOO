package backend.Action;

import backend.CanvasState;
import backend.model.Figure;

public class PaintAction {
    private final ActionType action;
    private final Figure figure;
    private final CanvasState canvas;

    public PaintAction(ActionType action, Figure figure, CanvasState canvas){
        this.action = action;
        this.figure = figure;
        this.canvas = canvas;
    }

    public void undo(){
        action.undo(canvas);
        canvas.toRedo(action,figure);
        canvas.deleteUndoAction();
    }

    public Figure getFigure() {
        return figure;
    }
}
