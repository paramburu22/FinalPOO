package backend.model;

import javafx.scene.paint.Color;

public abstract class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight, Color lineColor, Color backGroundColor, double lineWidth) {
        super(lineColor,backGroundColor,lineWidth);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void increase() {
        double xDiff = (topLeft.getX() - bottomRight.getX()) * 0.1;
        double yDiff = (topLeft.getY() - bottomRight.getY()) * 0.1;
        topLeft.setX(topLeft.getX() + xDiff);
        topLeft.setY(topLeft.getY() + yDiff);
        bottomRight.setX(bottomRight.getX() - xDiff);
        bottomRight.setY(bottomRight.getY() - yDiff);
    }
    @Override
    public void decrease() {
        double xDiff = (topLeft.getX() - bottomRight.getX()) * 0.1;
        double yDiff = (topLeft.getY() - bottomRight.getY()) * 0.1;
        topLeft.setX(topLeft.getX() - xDiff);
        topLeft.setY(topLeft.getY() - yDiff);
        bottomRight.setX(bottomRight.getX() + xDiff);
        bottomRight.setY(bottomRight.getY() + yDiff);
    }

    @Override
    public boolean containsOn(Point point){
        return point.getX() > getTopLeft().getX() && point.getX() < getBottomRight().getX() &&
                point.getY() > getTopLeft().getY() && point.getY() < getBottomRight().getY();
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

    @Override
    public String getFigureName(){
        return "Rectangulo";
    }

    @Override
    public void move(double diffX, double diffY){
        getTopLeft().x += diffX;
        getBottomRight().x += diffX;
        getTopLeft().y += diffY;
        getBottomRight().y += diffY;
    }

}

