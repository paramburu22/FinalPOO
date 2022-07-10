package backend;

import backend.Action.ActionType;
import backend.Action.PaintAction;
import backend.Exception.NothingSelectedException;
import backend.Exception.NothingToDoException;
import backend.model.Figure;

import java.util.*;
import java.util.List;

public class CanvasState {
    private final Deque<PaintAction> unDo = new LinkedList<>();

    private final Deque<PaintAction> reDo = new LinkedList<>();

    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    //Agrega acciones a unDo
    public void toUndo(ActionType action, Figure figure) throws NothingSelectedException {
        if(figure == null)
            throw new NothingSelectedException(action.toString());
        unDo.add(new PaintAction(action, figure.clone(), list.indexOf(figure)));
        //Cada vez que se agrega una accion a undo, vacio la cola de redo
        reDo.clear();
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public List<Figure> figures() {
        return new ArrayList<>(list);
    }

    public void undoAction() throws NothingToDoException {
        if(unDo.size() == 0)
            throw new NothingToDoException("Deshacer");
        PaintAction action = unDo.getLast();
        unDo.removeLast();
        //Si es DELETE solo vuelvo a agregarla a la lista en la misma posicion en la que estaba
        if(action.getActionType() == ActionType.DELETE) {
            replaceFigureInList(action);
            reDo.add(new PaintAction(action.getActionType(), list.get(action.getIndex()).clone(), action.getIndex()));
            return;
        }
        //Si es DRAW solo elimino la figura
        if(action.getActionType() == ActionType.DRAW) {
            reDo.add(new PaintAction(action.getActionType(), list.get(action.getIndex()).clone(), action.getIndex()));
            list.remove(action.getIndex());
            return;
        }
        reDo.add(new PaintAction(action.getActionType(), list.get(action.getIndex()).clone(), action.getIndex()));
        //Si no es DELETE ni DRAW seteo en la lista la vieja figura
        list.set(action.getIndex(), action.getOldFigure());
    }

    public PaintAction getUndoLastAction() {
        return unDo.getLast();
    }

    public int getUnDoSize() {
        return unDo.size();
    }

    public Deque<PaintAction> getUnDo(){
        return unDo;
    }


    public void redoAction() throws NothingToDoException {
        if(reDo.size() == 0)
            throw new NothingToDoException("Rehacer");
       PaintAction action = reDo.getLast();
       reDo.removeLast();
       if(action.getActionType() == ActionType.DRAW) {
           replaceFigureInList(action);
           unDo.add(new PaintAction(action.getActionType(), list.get(action.getIndex()).clone(), action.getIndex()));
           return;
       }
       Figure undoOldFigure = list.get(action.getIndex()).clone();
       if(action.getActionType() == ActionType.DELETE) {
           unDo.add(new PaintAction(action.getActionType(), undoOldFigure, action.getIndex()));
           list.remove(action.getIndex());
           return;
       }
       list.set(action.getIndex(), action.getOldFigure());
       unDo.add(new PaintAction(action.getActionType(), undoOldFigure, action.getIndex()));
    }

    public void replaceFigureInList(PaintAction action) {
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
    }

    public int getReDoSize() { return reDo.size(); }

    public PaintAction getRedoLastAction() { return reDo.getLast(); }
}
