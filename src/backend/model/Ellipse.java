package backend.model;

import javafx.scene.paint.Color;

public abstract class Ellipse extends Figure {

    protected final Point centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color lineColor, Color backGroundColor, double lineWidth) {
        super(lineColor,backGroundColor,lineWidth);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public void increase() {
        sMayorAxis += (0.1 *sMayorAxis);
        sMinorAxis += (0.1 *sMinorAxis);
    }
    @Override
    public void decrease() {
        sMayorAxis -= (0.1 *sMayorAxis);
        sMinorAxis -= (0.1 *sMinorAxis);
    }


    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }
}

