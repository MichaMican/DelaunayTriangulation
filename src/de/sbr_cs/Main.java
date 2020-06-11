package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	    Triangle t1 = new Triangle(new Point(2,0), new Point(1,2), new Point(2,3));
	    Triangle t2 = new Triangle(new Point(2,0), new Point(2,3), new Point(3,2));

	    if(t1.isNeighbour(t2)){
			try {

				Plot2DPanel plot = new Plot2DPanel();

				t1.draw(plot);
				t2.draw(plot);

				JFrame frame = new JFrame("a plot panel");
				frame.setContentPane(plot);
				frame.setVisible(true);


				TriangleNeighbours triangleNeighbours = new TriangleNeighbours(t1, t2);
				System.out.println("Current Delaunay Status: "+triangleNeighbours.isDelaunayConform());

				FlipHelper flipHelper = new FlipHelper();

				flipHelper.flipTrianglesNeighbours(triangleNeighbours);

				System.out.println("Delaunay Status after rotation: "+triangleNeighbours.isDelaunayConform());


			} catch (InvalidTriangleNeighboursException | InvalidPointException e) {
				e.printStackTrace();
			}
		}
    }
}
