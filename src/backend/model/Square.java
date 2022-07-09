package backend.model;

import javafx.scene.paint.Color;

public abstract class Square extends Rectangle {

    private double size;

    public Square(Point topLeft, double size, Color lineColor, Color backGroundColor, double lineWidth) {
        super(topLeft,new Point(topLeft.x + size, topLeft.y + size),lineColor,backGroundColor,lineWidth);
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", this.getTopLeft(), this.getBottomRight());
    }

    @Override
    public boolean containsOn(Point point){
        return point.getX() > getTopLeft().getX() && point.getX() < getBottomRight().getX() &&
                point.getY() > getTopLeft().getY() && point.getY() < getBottomRight().getY();
    }
    @Override
    public String getFigureName(){
        return "Cuadrado";
    }

    public double getSize() {
        return size;
    }
}
