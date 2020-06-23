package de.sbr_cs;

import de.sbr_cs.Interface.Drawable;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI handling class
 */
public class GUI {

    //================================================================================
    // Properties
    //================================================================================

    private JFrame frame;
    private Plot2DPanel plot;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Create new GUI object
     */
    public GUI(){
        plot = new Plot2DPanel();

        frame = new JFrame("Delaunay triangulation");
        frame.setSize(700,700);
        frame.setContentPane(plot);
        frame.setVisible(true);

        resetAll();
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Removes all drawings from UI
     */
    public void resetAll(){
        plot.removeAllPlots();
        //Make the coosys fixed size
        plot.addLinePlot("", Color.BLACK, new double[] {-10, -10}, new double[] {-10, -10});
        plot.addLinePlot("", Color.BLACK, new double[] {10, 10}, new double[] {10, 10});
    }

    /**
     * Draws a List of drawables in their standard color
     * @param toDraw List of drawables to draw
     */
    public void draw(List<? extends Drawable> toDraw){
        for (Drawable d: toDraw) {
            draw(d);
        }
    }

    /**
     * Draws drawable in its standard color
     * @param toDraw Drawable to draw
     */
    public void draw(Drawable toDraw){
        toDraw.draw(plot);
        frame.repaint();
    }

    /**
     * Draws drawable in provided color
     * @param toDraw Drawable to draw
     * @param color Color the drawable should have
     */
    public void draw(Drawable toDraw, Color color){
        toDraw.draw(plot, color);
        frame.repaint();
    }


}
