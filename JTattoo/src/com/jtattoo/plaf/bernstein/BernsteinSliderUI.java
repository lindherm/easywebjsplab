/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.BaseSliderUI;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BernsteinSliderUI extends BaseSliderUI {

    public BernsteinSliderUI(JSlider slider) {
        super(slider);
    }

    public static ComponentUI createUI(JComponent c) {
        return new BernsteinSliderUI((JSlider) c);
    }

    public void paintBackground(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            Component parent = c.getParent();
            if ((parent != null) && (parent.getBackground() instanceof ColorUIResource)) {
                BernsteinUtils.fillComponent(g, c);
            } else {
                if (parent != null) {
                    g.setColor(parent.getBackground());
                } else {
                    g.setColor(c.getBackground());
                }
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        }
    }
}
