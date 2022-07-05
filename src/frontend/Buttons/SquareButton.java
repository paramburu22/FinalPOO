package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.paint.Color;

public class SquareButton extends FigureToggleButton {
    public SquareButton(String name) {
        super(name);
    }


    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth) {
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new Square(startPoint, size,lineColor,backGroundColor,lineWidth);
    }
}
