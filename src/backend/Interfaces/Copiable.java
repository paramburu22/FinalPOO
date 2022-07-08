package backend.Interfaces;

import backend.model.Figure;

public interface Copiable {
    Figure copy(Color lineColor, Color backGroundColor,double lineWidth);
}
