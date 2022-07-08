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
        if(action != ActionType.DELETE)
            canvas.deleteLastFigure();
        if(action != ActionType.DRAW)
            canvas.redrawFigure();
        //action.undo(canvas);
        //canvas.toRedo(action, canvas.getListFigure(0));
        canvas.deleteUndoAction();
    }

    public Figure getUndoFigure() {
        return figure;
    }

    @Override
    public String toString(){
        return String.format("%s %s",action,figure.getFigureName());
    }
}
