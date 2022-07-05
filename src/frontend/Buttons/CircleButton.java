package frontend.Buttons;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.models.DrawableCircle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleButton extends FigureToggleButton{

    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableCircle(startPoint, circleRadius,lineColor,backGroundColor,lineWidth,gc);
    }
}
