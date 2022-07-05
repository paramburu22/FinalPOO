package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;

public class SquareButton extends FigureToggleButton {
    public SquareButton(String name) {
        super(name);
    }


    @Override
    public Figure make(Point startPoint, Point endPoint) {
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new Square(startPoint, size);
    }
}
