package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Triangle implements Drawable {
    private List<Point> points;
    private List<Triangle> neighbours;

    public Triangle(Point point1, Point point2, Point point3) {
        this.points = Arrays.asList(new Point[]{point1, point2, point3});
        this.neighbours = new ArrayList<>();
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this(new Point(x1,y1), new Point(x2,y2), new Point(x3,y3));
    }

    public void addNeighbour(Triangle t) throws InvalidTriangleNeighboursException {
        if(this.isNeighbour(t)){
            if(!neighbours.contains(t)){
                neighbours.add(t);
            }
        } else {
            throw new InvalidTriangleNeighboursException();
        }
    }

    private void removeNeighbour(Triangle triangle) {
        neighbours.remove(triangle);
    }

    //returns if a flip hat to be performed
    public boolean attainDelauneyForNeighbours(){
        boolean flipWasPerformed = false;
        for(Triangle t : neighbours){
            try {
                flipWasPerformed |= FlipHelper.flipTrianglesNeighbours(this,t);
            } catch (InvalidTriangleNeighboursException e) {
                e.printStackTrace();
            }
        }
        return flipWasPerformed;
    }

    public List<Triangle> getNeighbours() {
        return neighbours;
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

    // returns the angle on the opposite side of the line segment
    public double getOppositeSideAngle(Point p1, Point p2) {

        //get the opposite point
        Point p3 = points.stream().filter(point -> !point.equals(p1) && !point.equals(p2)).collect(Collectors.toList()).get(0);

        return calculateAngle(p3, p1, p2);
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

    @Override
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

        p1.draw(plot);
        p2.draw(plot);
        p3.draw(plot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(points, triangle.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    public boolean isDelaunayConform(Triangle neighbourTriangle) throws InvalidTriangleNeighboursException {

        if(!this.isNeighbour(neighbourTriangle)){
            throw new InvalidTriangleNeighboursException("The triangles aren't neighbours");
        }

        List<Point> contactPoints = getContactPoints(neighbourTriangle);

        return 180 >= (this.getOppositeSideAngle(contactPoints.get(0), contactPoints.get(1)) +
                neighbourTriangle.getOppositeSideAngle(contactPoints.get(0), contactPoints.get(1)));
    }

    public List<Point> getContactPoints(Triangle neighbourTriangle){

        List<Point> overlappingPoints = new ArrayList<Point>();
        List<Point> neighbourPoints = neighbourTriangle.getPoints();

        for (Point p : this.getPoints()) {
            if(neighbourPoints.contains(p)){
                overlappingPoints.add(p);
            }
        }

        return overlappingPoints;
    }


    public void updateNeighbours(List<Triangle> neighbourPool) {



        //remove old Neighbours
        List<Triangle> neighboursToRemove = new ArrayList<>();
        for(Triangle t : neighbours){
            if(!isNeighbour(t)){
                neighboursToRemove.add(t);
                t.removeNeighbour(this);
            }
        }
        for(Triangle t : neighboursToRemove){
            neighbours.remove(t);
        }

        //add new Neighbours
        for(Triangle t : neighbourPool){
            if(isNeighbour(t) && !this.equals(t)){
                if(!neighbours.contains(t)){
                    neighbours.add(t);
                    try {
                        t.addNeighbour(this);
                    } catch (InvalidTriangleNeighboursException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }


}
