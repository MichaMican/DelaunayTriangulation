package de.sbr_cs;

import de.sbr_cs.Interface.Drawable;
import org.math.plot.Plot2DPanel;

import java.awt.*;
import java.util.Objects;

/**
 * Point object
 */
public class Point implements Drawable {

    //================================================================================
    // Properties
    //================================================================================

    private double x;
    private double y;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Create new point object
     * @param x x Coordinate
     * @param y y Coordinate
     */
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    //================================================================================
    // Accessors
    //================================================================================

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Callculate distance to point
     * @param p point to which the distance should be callculated
     * @return distance between this point and point p
     */
    public double getDistance(Point p){
        return Math.sqrt(Math.pow((p.getX() - x), 2) + Math.pow((p.getY() - y), 2));
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public void draw(Plot2DPanel plot){
        draw(plot, Color.RED);
    }

    @Override
    public void draw(Plot2DPanel plot, Color color) {
        double size = 0.5;

        plot.addLinePlot("", color, new double[] {x-size/2,y+size/2}, new double[] {x+size/2,y-size/2});
        plot.addLinePlot("", color, new double[] {x-size/2,y-size/2}, new double[] {x+size/2,y+size/2});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
