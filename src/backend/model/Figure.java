package backend.model;

import javafx.scene.paint.Color;

public abstract class Figure {

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
}
