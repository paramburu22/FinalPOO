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
        System.out.println(String.format("%s %s", action.toString(), listFigure.getFigureName()));
    }

    public void undoAction() {
        PaintAction action = unDo.getLast();
        unDo.removeLast();
      //  toRedo(action.getActionType(), list.get(getLastAction().getIndex()).clone(), );
        //si es DRAW solo elimino la figura
        if(action.getActionType() == ActionType.DRAW) {
            System.out.println("removiendo ");
            list.remove(action.getIndex());
            return;
        }
        //si es DELETE vuelvo a agregarla a la lista en la misma posicion en la que estaba
        if(action.getActionType() == ActionType.DELETE) {
            Figure last = action.getOldFigure();
            for (int i = action.getIndex(); i <= list.size(); i++) {
                if(i == list.size()) {
                    list.add(last);
                    return;
                }
                Figure current = list.get(i);
                list.set(i, last);
                last = current;
            }
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

    public Deque<PaintAction> getUnDo(){
        return unDo;
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
