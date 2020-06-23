package de.sbr_cs.Interface;

import org.math.plot.Plot2DPanel;

import java.awt.*;

public interface Drawable {
    /**
     * Draw object in standard color
     * @param plot Plot object where the object should be drawn to
     */
    void draw(Plot2DPanel plot);

    /**
     * Draw object in specified color
     * @param plot Plot object where the object should be drawn to
     * @param color Color the object should have
     */
    void draw(Plot2DPanel plot, Color color);
}
