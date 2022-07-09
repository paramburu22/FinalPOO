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

    private final List<PaintAction> reDo = new ArrayList<>();

    private final List<Figure> list = new ArrayList<>();


    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void toUndo(ActionType actionType,Figure figure) {
        unDo.add(new PaintAction(actionType,figure, this, list.indexOf(figure)));
    }

    public void toRedo(ActionType actionType,Figure figure) {
        reDo.add(new PaintAction(actionType,figure, this, list.lastIndexOf(figure)));
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public PaintAction deleteUndoAction() {
        return unDo.removeLast();
    }

    public void redrawFigure() {
        addFigure(unDo.getLast().getUndoFigure());
    }

    public void deleteFigureByIdx(int idx){
        if(list.size()!=0) {
            System.out.println("BORRANDO FIGURA VIEJA");
            deleteFigure(list.get(idx));
        }
    }

    public PaintAction getLastAction(){
        return unDo.getLast();
    }

    public int getUnDoSize(){
        return unDo.size();
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
