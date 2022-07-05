package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.paint.Color;

public class RectangleButton extends FigureToggleButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth) {
        return new Rectangle(startPoint, endPoint,lineColor,backGroundColor,lineWidth);
    }
}
