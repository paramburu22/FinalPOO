package backend.model;

import javafx.scene.paint.Color;

public abstract class Circle extends Ellipse {

    private double radius;
    public Circle(Point centerPoint, double radius, Color lineColor, Color backGroundColor, double lineWidth) {
        super(centerPoint, 2 * radius, 2 * radius,lineColor,backGroundColor,lineWidth);
        this.radius = radius;
    }


    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s , Radio: %.2f}]", centerPoint, radius);
    }

    @Override
    public String getFigureName(){
        return "Círculo";
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }
    @Override
    public void increase() {
        radius += (0.1 *radius);
    }
    @Override
    public void decrease() {
        radius -= (0.1*radius);
    }

    @Override
    public boolean containsOn(Point point){
        return Math.sqrt(Math.pow(getCenterPoint().getX() - point.getX(), 2) +
                Math.pow(getCenterPoint().getY() - point.getY(), 2)) < getRadius();
    }

}

