package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI {

    private JFrame frame;
    private Plot2DPanel plot;

    public GUI(){
        plot = new Plot2DPanel();

        frame = new JFrame("Delaunay triangulation");
        frame.setSize(700,700);
        frame.setContentPane(plot);
        frame.setVisible(true);

        resetAll();
    }

    public void resetAll(){
        plot.removeAllPlots();
        //Make the coosys fixed size
        plot.addLinePlot("", Color.BLACK, new double[] {-10, -10}, new double[] {-10, -10});
        plot.addLinePlot("", Color.BLACK, new double[] {10, 10}, new double[] {10, 10});
    }

    public void draw(List<? extends Drawable> toDraw){
        for (Drawable d: toDraw) {
            draw(d);
        }
    }

    public void draw(Drawable toDraw){
        toDraw.draw(plot);
        frame.repaint();
    }


}
