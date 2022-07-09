package backend;

import backend.Action.ActionType;
import backend.Action.PaintAction;
import backend.model.Figure;

import java.util.*;

public class CanvasState {

    /*private Figure selectedFigure;

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }
    public Figure getSelectedFigure(){
        return selectedFigure;
    }*/

    private final Deque<PaintAction> unDo = new LinkedList<>();

    private final Queue<PaintAction> reDo = new LinkedList<>();

    private final List<Figure> list = new ArrayList<>();


    public void addFigure(Figure figure) {
        list.add(figure);
    }

    //agrega acciones (ActionType, ) a unDo
    public void toUndo(ActionType action, Figure oldFigure, Figure listFigure) {
        unDo.add(new PaintAction(action, oldFigure, list.indexOf(listFigure)));
    }

    public void undoAction() {
        PaintAction action = unDo.getLast();
        reDo.add(action);
        if(action.getOldFigure() == null) {
            System.out.println("removiendo ");
            list.remove(action.getIndex());
            return;
        }
        list.set(action.getIndex(), action.getOldFigure());
    }

    public PaintAction getLastAction() {
        return unDo.getLast();
    }

    public int getUnDoSize() {
        return unDo.size();
    }

    public void toRedo() {

    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
