package backend.Action;

import backend.CanvasState;
import backend.model.Figure;

public class PaintAction {
    private final ActionType actionType;
    private final Figure oldFigure; //figura que queremos reemplazar al apretar deshacer
    private final int idx; //indice donde se encuentra la figura a modificar en list

    public PaintAction(ActionType action, Figure oldFigure, int idx){
        this.oldFigure = oldFigure;
        this.actionType = action;
        this.idx = idx;
    }

    public int getIndex() {
        return idx;
    }

    public Figure getOldFigure() {
        return oldFigure;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return String.format("%s %s",actionType,oldFigure.getFigureName());
    }
}
