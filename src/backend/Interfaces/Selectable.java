package backend.Interfaces;

import backend.model.Point;

@FunctionalInterface
public interface Selectable {
    boolean containsOn(Point point);
}
