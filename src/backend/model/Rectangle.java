package backend.model;

import javafx.scene.paint.Color;

public abstract class Rectangle extends Figure {

    protected Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight, Color lineColor, Color backGroundColor, double lineWidth) {
        super(lineColor,backGroundColor,lineWidth);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void increase() {
        topLeft.setX(topLeft.getX() - 0.1* topLeft.getX());
        topLeft.setY(topLeft.getY() - 0.1* topLeft.getY());
        bottomRight.setX(bottomRight.getX() + 0.1* bottomRight.getX());
        bottomRight.setY(bottomRight.getY() + 0.1* bottomRight.getY());
    }
    @Override
    public void decrease() {
        topLeft.setX(topLeft.getX() + 0.1* topLeft.getX());
        topLeft.setY(topLeft.getY() + 0.1* topLeft.getY());
        bottomRight.setX(bottomRight.getX() - 0.1* bottomRight.getX());
        bottomRight.setY(bottomRight.getY() - 0.1* bottomRight.getY());
    }


    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }
    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }


}

