package backend.model;

import javafx.scene.paint.Color;

public class Circle extends Ellipse {

    private double radius;
    public Circle(Point centerPoint, double radius, Color lineColor, Color backGroundColor, double lineWidth) {
        super(centerPoint, 2 * radius, 2 * radius,lineColor,backGroundColor,lineWidth);
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", centerPoint, radius);
    }


    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }
}

