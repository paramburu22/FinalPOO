package backend.Action;

import backend.model.Figure;

public class PaintAction {
    private final ActionType action;
    private final Figure figure;
    public PaintAction(ActionType action, Figure figure){
        this.action = action;
        this.figure = figure;
    }
}
