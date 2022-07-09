package backend.model;

import javafx.scene.paint.Color;

public abstract class Square extends Rectangle {


    public Square(Point topLeft, Point bottomRight, Color lineColor, Color backGroundColor, double lineWidth) {
        super(topLeft,bottomRight,lineColor,backGroundColor,lineWidth);
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

}
