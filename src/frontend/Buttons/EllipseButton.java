package frontend.Buttons;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

public class EllipseButton extends FigureToggleButton {

    public EllipseButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth) {
        Point centerPoint = new Point(Math.abs(endPoint.x + startPoint.x) / 2, (Math.abs((endPoint.y + startPoint.y)) / 2));
        double sMayorAxis = Math.abs(endPoint.x - startPoint.x);
        double sMinorAxis = Math.abs(endPoint.y - startPoint.y);
        return new Ellipse(centerPoint, sMayorAxis, sMinorAxis,lineColor,backGroundColor,lineWidth);
    }
}
