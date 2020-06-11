package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	    Triangle t1 = new Triangle(new Point(2,0), new Point(1,2), new Point(2,3));
	    Triangle t2 = new Triangle(new Point(2,0), new Point(2,3), new Point(3,2));

	    GUI gui = new GUI();

	    if(t1.isNeighbour(t2)){
			try {
				Plot2DPanel plot = new Plot2DPanel();

				gui.draw(t1);
				gui.draw(t2);
				gui.draw(t1.getPoints());
				gui.draw(t2.getPoints());

				Thread.sleep(2000);

				TriangleNeighbours triangleNeighbours = new TriangleNeighbours(t1, t2);
				System.out.println("Current Delaunay Status: "+triangleNeighbours.isDelaunayConform());

				FlipHelper flipHelper = new FlipHelper();

				flipHelper.flipTrianglesNeighbours(triangleNeighbours);

				System.out.println("Delaunay Status after rotation: "+triangleNeighbours.isDelaunayConform());

				gui.resetAll();

				gui.draw(t1);
				gui.draw(t2);
				gui.draw(t1.getPoints());
				gui.draw(t2.getPoints());

				Thread.sleep(2000);
				System.exit(0);


			} catch (InvalidTriangleNeighboursException | InvalidPointException | InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
}
