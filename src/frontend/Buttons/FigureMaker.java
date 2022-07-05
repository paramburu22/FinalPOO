package frontend.Buttons;

import backend.model.Figure;
import backend.model.Point;

public interface FigureMaker {
    Figure make(Point startPoint, Point endPoint);
}
