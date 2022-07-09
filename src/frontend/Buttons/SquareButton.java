package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.models.DrawableSquare;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareButton extends FigureToggleButton {
    public SquareButton(String name) {
        super(name);
    }


    @Override
    public Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        return new DrawableSquare(startPoint, endPoint,lineColor,backGroundColor,lineWidth,gc);
    }
}
