package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;

public class RectangleButton extends FigureToggleButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }
}
