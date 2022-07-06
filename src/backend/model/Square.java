package backend.model;

import javafx.scene.paint.Color;

public abstract class Square extends Rectangle {

    public Square(Point topLeft, double size, Color lineColor, Color backGroundColor, double lineWidth) {
        super(topLeft,new Point(topLeft.x + size, topLeft.y + size),lineColor,backGroundColor,lineWidth);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean containsOn(Point point){
        return point.getX() > getTopLeft().getX() && point.getX() < getBottomRight().getX() &&
                point.getY() > getTopLeft().getY() && point.getY() < getBottomRight().getY();
    }
}
