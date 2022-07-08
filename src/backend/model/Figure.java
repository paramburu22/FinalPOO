package backend.model;

import backend.Interfaces.Drawable;
import backend.Interfaces.Modificable;
import backend.Interfaces.Movable;
import backend.Interfaces.Selectable;
import javafx.scene.paint.Color;

public abstract class Figure implements Drawable, Modificable, Selectable, Movable {

    private Color lineColor;
    private Color backGroundColor;
    private double lineWidth;

    Figure(Color lineColor, Color backGroundColor,double lineWidth){
        this.backGroundColor = backGroundColor;
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
    }

    public double getLineWidth() {
        return lineWidth;
    }


    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public abstract String getFigureName();

}
