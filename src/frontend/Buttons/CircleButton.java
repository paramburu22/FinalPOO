package frontend.Buttons;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

public class CircleButton extends FigureToggleButton{

    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, circleRadius,lineColor,backGroundColor,lineWidth);
    }
}
