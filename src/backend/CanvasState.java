package backend;

import backend.Action.ActionType;
import backend.Action.PaintAction;
import backend.Exception.NothingSelectedException;
import backend.Exception.NothingToDoException;
import backend.model.Figure;

import java.util.*;
import java.util.List;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    //Tanto undo como redo son colas de PaintAction de manera que siempre se saca la ultima accion agregada
    private final Deque<PaintAction> unDo = new LinkedList<>();
    private final Deque<PaintAction> reDo = new LinkedList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    //Se llama cada vez que se realiza una accion del tipo DREW, DELETE, FILLCOLOR, LINECOLOR, INCREASE o DECRESE
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

    //Devuelvo la lista de figuras
    public List<Figure> figures() {
        return new ArrayList<>(list);
    }

    //Se llama al apretar el boton Deshacer
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

    //Se llama al apretar el boton Rehacer
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

    //Metodo utilizado cuando se hace undo de DELETE o cuando se hace redo de DRAW
    //Se quiere volver a poner la figura vieja en su posicion original en la lista, cambiando las figuras siguientes a una posicion mas
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
