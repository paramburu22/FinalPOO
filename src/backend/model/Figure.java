package backend.model;

import backend.Interfaces.Drawable;
import javafx.scene.paint.Color;

public abstract class Figure implements Drawable {

    private Color lineColor;
    private Color backGroundColor;
    private double lineWidth;
    private double size;

    Figure(Color lineColor, Color backGroundColor,double lineWidth){
        this.backGroundColor = backGroundColor;
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
        this.size = 1;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public double getSize() {
        return size;
    }

    public void increase() {
        this.size += 0.1;
    }

    public void decrease() { this.size -= 0.1; }

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

}
