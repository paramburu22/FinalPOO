package backend;

import backend.Action.ActionType;
import backend.Action.PaintAction;
import backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    /*private Figure selectedFigure;

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }
    public Figure getSelectedFigure(){
        return selectedFigure;
    }*/

    private final List<PaintAction> unDo = new ArrayList<>();

    private final List<PaintAction> reDo = new ArrayList<>();

    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void toUndo(ActionType actionType,Figure figure) {
        unDo.add(new PaintAction(actionType,figure));
    }

    public void toRedo(ActionType actionType,Figure figure) {
        reDo.add(new PaintAction(actionType,figure));
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
