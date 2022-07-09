package frontend.models;

import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableSquare extends Square {
    private GraphicsContext gc;
    public DrawableSquare(Point topLeft, double size, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        super(topLeft, size, lineColor, backGroundColor, lineWidth);
        this.gc = gc;
    }

    @Override
    public void draw() {
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public DrawableSquare clone() {
        return new DrawableSquare(this.getTopLeft(), this.getSize(), this.getLineColor(), this.getBackGroundColor(), this.getLineWidth(), this.gc);
    }
}
