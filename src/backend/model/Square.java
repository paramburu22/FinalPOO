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

}
