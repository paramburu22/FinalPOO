package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

public interface FigureMaker {
    Figure make(Point startPoint, Point endPoint, Color lineColor, Color backGroundColor, double lineWidth);
}
