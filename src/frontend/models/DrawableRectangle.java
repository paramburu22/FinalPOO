package frontend.models;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableRectangle extends Rectangle {

    private GraphicsContext gc;

    public DrawableRectangle(Point topLeft, Point bottomRight, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        super(topLeft, bottomRight, lineColor, backGroundColor, lineWidth);
        this.gc = gc;
    }

    @Override
    public void draw() {
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(), Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(), Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public DrawableRectangle clone() {
        return new DrawableRectangle(this.getTopLeft(), this.getBottomRight(), this.getLineColor(), this.getBackGroundColor(), this.getLineWidth(), this.gc);
    }
}
