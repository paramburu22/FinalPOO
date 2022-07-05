package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.models.DrawableRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleButton extends FigureToggleButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        return new DrawableRectangle(startPoint, endPoint,lineColor,backGroundColor,lineWidth,gc);
    }
}
