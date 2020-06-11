package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triangle implements Drawable {
    private List<Point> points;
    private TriangleNeighbours neighbour1 = null;
    private TriangleNeighbours neighbour2 = null;
    private TriangleNeighbours neighbour3 = null;

    public Triangle(Point point1, Point point2, Point point3) {
        this.points = Arrays.asList(new Point[]{point1, point2, point3});
    }

    public List<Point> getPoints() {
        //this prevents write access to points List
        return new ArrayList<Point>(points);
    }

    public void replacePoint(Point oldPoint, Point newPoint) throws InvalidPointException {
        if (points.contains(oldPoint)) {
            points.set(points.indexOf(oldPoint), newPoint);
        } else {
            throw new InvalidPointException(String.format("Triangle does not contain Point (%f|%f)", oldPoint.getX(), oldPoint.getY()));
        }
    }

    private double calculateAngle(Point ankerPoint, Point p1, Point p2) {

        //direction Vector from ankerPoint to p1
        double x1 = p1.getX() - ankerPoint.getX();
        double y1 = p1.getY() - ankerPoint.getY();
        //direction Vector from ankerPoint to p2
        double x2 = p2.getX() - ankerPoint.getX();
        double y2 = p2.getY() - ankerPoint.getY();

        //use dot product to callculate angle between the two vectors
        return Math.toDegrees(Math.acos((x1 * x2 + y1 * y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2))));
    }

    public double getLargestAngle() {
        double a1 = calculateAngle(points.get(0), points.get(1), points.get(2));
        double a2 = calculateAngle(points.get(1), points.get(0), points.get(2));
        double a3 = calculateAngle(points.get(2), points.get(0), points.get(1));

        return Math.max(a1, Math.max(a2, a3));
    }

    public boolean isNeighbour(Triangle triangle) {
        short overlappingPoints = 0;

        List<Point> neighbourPoints = triangle.getPoints();

        for (Point p : points) {
            if (neighbourPoints.contains(p)) {
                overlappingPoints++;
            }
        }

        return overlappingPoints >= 2;
    }

    public void draw(Plot2DPanel plot){
        draw(plot, Color.BLUE);
    }

    @Override
    public void draw(Plot2DPanel plot, Color color) {
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        Point p3 = points.get(2);

        plot.addLinePlot("", color, new double[] {p1.getX(), p1.getY()}, new double[] {p2.getX(), p2.getY()});
        plot.addLinePlot("", color, new double[] {p2.getX(), p2.getY()}, new double[] {p3.getX(), p3.getY()});
        plot.addLinePlot("", color, new double[] {p3.getX(), p3.getY()}, new double[] {p1.getX(), p1.getY()});
    }


}
