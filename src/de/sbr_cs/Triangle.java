package de.sbr_cs;

import de.sbr_cs.Exception.InvalidPointException;
import de.sbr_cs.Exception.InvalidTriangleNeighboursException;
import de.sbr_cs.Helper.FlipHelper;
import de.sbr_cs.Interface.Drawable;
import org.math.plot.Plot2DPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Triangle object
 */
public class Triangle implements Drawable {

    //================================================================================
    // Properties
    //================================================================================

    private List<Point> points;
    private List<Triangle> neighbours;

    //================================================================================
    // Constructors
    //================================================================================

    public Triangle(Point point1, Point point2, Point point3) {
        this.points = Arrays.asList(new Point[]{point1, point2, point3});
        this.neighbours = new ArrayList<>();
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this(new Point(x1,y1), new Point(x2,y2), new Point(x3,y3));
    }

    //================================================================================
    // Accessors
    //================================================================================

    /**
     * Gets neighbours of triangle
     * @return Neigbours of triangle
     */
    public List<Triangle> getNeighbours() {
        //prevents write access to points List
        return new ArrayList<>(neighbours);
    }

    /**
     * Gets points the Triangle consists of
     * @return List of points which define the triangle
     */
    public List<Point> getPoints() {
        //prevents write access to points List
        return new ArrayList<>(points);
    }

    /**
     * Registeres neighbour triangle
     * @param t Neighbour triangle
     * @throws InvalidTriangleNeighboursException Is thrown if the triangle isn't a neighbour
     */
    public void addNeighbour(Triangle t) throws InvalidTriangleNeighboursException {
        if(this.isNeighbour(t)){
            if(!neighbours.contains(t)){
                neighbours.add(t);
            }
        } else {
            throw new InvalidTriangleNeighboursException();
        }
    }

    /**
     * Removes triangle from neighbours
     * @param triangle Triangle to remove
     */
    private void removeNeighbour(Triangle triangle) {
        neighbours.remove(triangle);
    }

    /**
     * Replace point of triangle with new point
     * @param oldPoint Point to be replaced
     * @param newPoint Point replaced with
     * @throws InvalidPointException Is thrown when old point is not a part of the triangle
     */
    public void replacePoint(Point oldPoint, Point newPoint) throws InvalidPointException {
        if (points.contains(oldPoint)) {
            points.set(points.indexOf(oldPoint), newPoint);
        } else {
            throw new InvalidPointException(String.format("Triangle does not contain Point (%f|%f)", oldPoint.getX(), oldPoint.getY()));
        }
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Check if triangle is a neighbour triangle
     * @param triangle Triangle to check
     * @return boolean value if triangle is neighbour of triangle
     */
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

    /**
     * Attains Delauney conformity for neighbour triangles
     * @return if a Flip was performed
     */
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

    /**
     * Check if triangle is delauney conform with neighbour triangle
     * @param neighbourTriangle Neighbour triangle
     * @return Delauney conformity
     * @throws InvalidTriangleNeighboursException thrown if neighbour triangle is not a direct neighbour (two overlapping points)
     */
    public boolean isDelaunayConform(Triangle neighbourTriangle) throws InvalidTriangleNeighboursException {
        if(!this.isNeighbour(neighbourTriangle)){
            throw new InvalidTriangleNeighboursException("The triangles aren't neighbours");
        }

        List<Point> contactPoints = getContactPoints(neighbourTriangle);

        return 180 >= (this.getOppositeSideAngle(contactPoints.get(0), contactPoints.get(1)) +
                neighbourTriangle.getOppositeSideAngle(contactPoints.get(0), contactPoints.get(1)));
    }

    /**
     * Calculates angle around one anker point
     * @param ankerPoint Point the angle is arround
     * @param p1 Point 1 that defines first line segment together with the anker point
     * @param p2 Point 2 that defines second line segment together with the anker point
     * @return angle between ankerPoint|P1 & ankerPoint|P2 in DEGREE
     */
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

    /**
     * returns the angle on the opposite side of the line segment
     * @param p1 Point 1 of line segment
     * @param p2 Point 1 of line segment
     * @return opposite side angle in DEGREE
     */
    public double getOppositeSideAngle(Point p1, Point p2) {

        //get the opposite point
        Point p3 = points.stream().filter(point -> !point.equals(p1) && !point.equals(p2)).collect(Collectors.toList()).get(0);

        return calculateAngle(p3, p1, p2);
    }

    /**
     * Get overlapping points
     * @param neighbourTriangle Overlapping triangle
     * @return List of Points that overlap
     */
    public List<Point> getContactPoints(Triangle neighbourTriangle){

        List<Point> overlappingPoints = new ArrayList<>();
        List<Point> neighbourPoints = neighbourTriangle.getPoints();

        for (Point p : this.getPoints()) {
            if(neighbourPoints.contains(p)){
                overlappingPoints.add(p);
            }
        }

        return overlappingPoints;
    }

    /**
     * Updates neighbours - adds new neighbours from the neighbour pool - removes old neighbours that aren't neighbours anymore
     * @param neighbourPool Potential (new) neighbours
     */
    public void updateNeighbours(List<Triangle> neighbourPool) {
        //remove old neighbours
        List<Triangle> neighboursToRemove = new ArrayList<>();
        for(Triangle t : neighbours){
            if(!isNeighbour(t)){
                neighboursToRemove.add(t);
                //also remove this triangle from the removed neighbour
                t.removeNeighbour(this);
            }
        }
        for(Triangle t : neighboursToRemove){
            neighbours.remove(t);
        }

        //add new neighbours
        for(Triangle t : neighbourPool){
            if(isNeighbour(t) && !this.equals(t)){
                if(!neighbours.contains(t)){
                    neighbours.add(t);
                    try {
                        //also adds this triangle to the added neighbour
                        t.addNeighbour(this);
                    } catch (InvalidTriangleNeighboursException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public void draw(Plot2DPanel plot){
        draw(plot, Color.BLUE);
    }

    @Override
    public void draw(Plot2DPanel plot, Color color) {
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        Point p3 = points.get(2);

        //Draws triangle lines
        plot.addLinePlot("", color, new double[] {p1.getX(), p1.getY()}, new double[] {p2.getX(), p2.getY()});
        plot.addLinePlot("", color, new double[] {p2.getX(), p2.getY()}, new double[] {p3.getX(), p3.getY()});
        plot.addLinePlot("", color, new double[] {p3.getX(), p3.getY()}, new double[] {p1.getX(), p1.getY()});

        //Draws triangle edge points
        p1.draw(plot);
        p2.draw(plot);
        p3.draw(plot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        //doesn't account for neighbours as they are not triangle defining (may be unset currently)
        return Objects.equals(points, triangle.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }


}
