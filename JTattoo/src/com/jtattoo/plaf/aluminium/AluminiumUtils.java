/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author  Michael Hagen
 */
public class AluminiumUtils {

    private AluminiumUtils() {
    }

    public static void fillComponent(Graphics g, Component c) {
        if (!JTattooUtilities.isMac() && AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
            Point offset = JTattooUtilities.getRelLocation(c);
            Dimension size = JTattooUtilities.getFrameSize(c);
            Graphics2D g2D = (Graphics2D) g;
            g2D.setPaint(new AluminiumGradientPaint(offset, size));
            g2D.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2D.setPaint(null);
        } else {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }

    public static void fillComponent(Graphics g, Component c, int x, int y, int w, int h) {
        Graphics2D g2D = (Graphics2D) g;
        Shape savedClip = g2D.getClip();
        Area clipArea = new Area(new Rectangle2D.Double(x, y, w, h));
        clipArea.intersect(new Area(savedClip));
        g2D.setClip(clipArea);
        fillComponent(g, c);
        g2D.setClip(savedClip);
    }
}
