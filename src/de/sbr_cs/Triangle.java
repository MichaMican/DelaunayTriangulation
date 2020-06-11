package de.sbr_cs;

public class Triangle {
    private Point p1;
    private Point p2;
    private Point p3;
    private boolean isDelaunayConform;

    public Triangle(Point point1, Point point2, Point point3) {
        this.p1 = point1;
        this.p2 = point2;
        this.p3 = point3;
        this.isDelaunayConform = delaunayConformityCheck();
    }


    public boolean isDelaunayConform() {
        return isDelaunayConform;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        this.isDelaunayConform = delaunayConformityCheck();
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
        this.isDelaunayConform = delaunayConformityCheck();
    }

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
        this.isDelaunayConform = delaunayConformityCheck();
    }

    //Returns if triangle is delaunayConform
    public boolean delaunayConformityCheck(){
        //TODO: implement Delaunay conformity check
    }

}
