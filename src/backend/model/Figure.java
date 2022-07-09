package backend.model;

import backend.Interfaces.*;
import frontend.Buttons.FigureMaker;
import javafx.scene.paint.Color;

public abstract class Figure implements Drawable, Modificable, Selectable, Movable {

    private Color lineColor;
    private Color backGroundColor;
    private double lineWidth;

    public Figure(Color lineColor, Color backGroundColor,double lineWidth){
        this.backGroundColor = backGroundColor;
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
    }

    public Figure(Figure original) {
        this.backGroundColor = original.backGroundColor;
        this.lineColor = original.lineColor;
        this.lineWidth = original.lineWidth;
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

    public abstract Figure clone();
}
