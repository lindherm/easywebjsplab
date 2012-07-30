/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.acryl;

import com.jtattoo.plaf.BaseButtonUI;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

/**
 * @author Michael Hagen
 */
public class AcrylButtonUI extends BaseButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new AcrylButtonUI();
    }

    protected void paintBackground(Graphics g, AbstractButton b) {
        int w = b.getWidth();
        int h = b.getHeight();
        Graphics2D g2D = (Graphics2D) g;
        Shape savedClip = g.getClip();
        if ((b.getBorder() != null) && b.isBorderPainted() && (b.getBorder() instanceof UIResource)) {
            Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, w -1, h - 1, 6, 6));
            clipArea.intersect(new Area(savedClip));
            g2D.setClip(clipArea);
        }
        super.paintBackground(g, b);
        g2D.setClip(savedClip);
    }
}


