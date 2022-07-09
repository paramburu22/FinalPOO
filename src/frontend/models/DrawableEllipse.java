package frontend.models;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DrawableEllipse extends Ellipse {
    private GraphicsContext gc;
    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color lineColor, Color backGroundColor, double lineWidth, GraphicsContext gc) {
        super(centerPoint, sMayorAxis, sMinorAxis, lineColor, backGroundColor, lineWidth);
        this.gc = gc;
    }

    @Override
    public void draw() {
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public DrawableEllipse clone() {
        return new DrawableEllipse(getCenterPoint(), getsMayorAxis() , getsMinorAxis() , getLineColor(), getBackGroundColor(), getLineWidth(), gc);
    }

}
