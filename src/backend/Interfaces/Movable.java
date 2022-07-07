package backend.Interfaces;

import backend.model.Point;

@FunctionalInterface
public interface Movable {
    void move(double diffX, double diffY);
}
