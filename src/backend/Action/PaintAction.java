package backend.Action;

import backend.CanvasState;
import backend.model.Figure;

public class PaintAction {
    private final ActionType action;
    private final Figure oldFigure;
    private final Figure newFigure;
    private final CanvasState canvas;

    public PaintAction(ActionType action, Figure oldFigure, Figure newFigure,CanvasState canvas){
        this.action = action;
        this.newFigure = newFigure;
        this.oldFigure = oldFigure;
        this.canvas = canvas;
    }

    public void undo() {
        if(action != ActionType.DELETE)
            canvas.deleteFigure(newFigure);
        if(action != ActionType.DRAW)
            canvas.redrawFigure(oldFigure);
        //action.undo(canvas);
        //canvas.toRedo(action, canvas.getListFigure(0));
    }

    public ActionType getActionType() {
        return action;
    }

    public Figure getNewFigure() {return newFigure;}

    public Figure getOldFigure() {
        return oldFigure;
    }

    @Override
    public String toString(){
        return String.format("%s %s",action,newFigure.getFigureName());
    }
}
