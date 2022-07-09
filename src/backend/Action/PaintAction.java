package backend.Action;

import backend.CanvasState;
import backend.model.Figure;

public class PaintAction {
    private final ActionType action;
    private final Figure figure;
    private final CanvasState canvas;
    private final int figureIdx;

    public PaintAction(ActionType action, Figure figure,CanvasState canvas,int figureIdx){
        this.action = action;
        this.figure = figure;
        this.canvas = canvas;
        this.figureIdx = figureIdx;
    }

    public void undo() {
        if(action != ActionType.DELETE)
            canvas.deleteFigureByIdx(figureIdx);
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
