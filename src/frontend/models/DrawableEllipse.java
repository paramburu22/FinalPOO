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
        double mAxis = getsMayorAxis()*getSize();
        double miAxis = getsMinorAxis()*getSize();
        gc.strokeOval(getCenterPoint().getX() - (mAxis / 2), getCenterPoint().getY() - (miAxis / 2), mAxis, miAxis);
        gc.fillOval(getCenterPoint().getX() - (mAxis / 2), getCenterPoint().getY() - (miAxis / 2), mAxis, miAxis);
    }
}
