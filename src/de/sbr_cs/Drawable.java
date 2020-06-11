package de.sbr_cs;

import org.math.plot.Plot2DPanel;

import java.awt.*;

public interface Drawable {
    void draw(Plot2DPanel plot);
    void draw(Plot2DPanel plot, Color color);
}
