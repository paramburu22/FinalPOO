package frontend.models;

import backend.model.Circle;
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
        double diameter = getSize() * getRadius() * 2;
        gc.fillOval(getCenterPoint().getX() - getRadius()*getSize() , getCenterPoint().getY() - getRadius()*getSize(), diameter, diameter);
        gc.strokeOval(getCenterPoint().getX() - getRadius()*getSize() , getCenterPoint().getY() - getRadius()*getSize(),  diameter, diameter);
    }
}
