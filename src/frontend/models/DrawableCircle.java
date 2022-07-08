package frontend.models;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCircle extends Circle {

    private GraphicsContext gc;

    public DrawableCircle(Point centerPoint, double radius, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        super(centerPoint, radius, lineColor, backGroundColor, lineWidth);
        this.gc = gc;
    }


    @Override
    public void draw() {
        double diameter = getRadius() * 2;
        gc.fillOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
        gc.strokeOval(getCenterPoint().getX() - getRadius() , getCenterPoint().getY() - getRadius(),  diameter, diameter);
    }

    public GraphicsContext getGc() {
        return gc;
    }


    @Override
    public DrawableCircle copy(DrawableCircle figure) {
        return new DrawableCircle(figure.get);
    }
}
