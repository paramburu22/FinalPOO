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
        double topLeftX = getSize() * getTopLeft().getX();
        double bottomRightX = getSize() * getBottomRight().getX();
        double topLeftY = getSize() * getTopLeft().getY();
        double bottomRightY = getSize() * getBottomRight().getY();

        gc.fillRect(topLeftX, topLeftY, Math.abs(topLeftX - bottomRightX), Math.abs(topLeftY - bottomRightY));
        gc.strokeRect(topLeftX, topLeftY, Math.abs(topLeftX - bottomRightX), Math.abs(topLeftY - bottomRightY));
    }
}
