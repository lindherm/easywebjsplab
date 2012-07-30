/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopPaneUI;

/**
 * @author Michael Hagen
 */
public class BaseDesktopPaneUI extends BasicDesktopPaneUI {

    private static BaseDesktopPaneUI desktopPaneUI = null;

    public static ComponentUI createUI(JComponent c) {
        if (desktopPaneUI == null) {
            desktopPaneUI = new BaseDesktopPaneUI();
        }
        return desktopPaneUI;
    }

    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            Object backgroundTexture = c.getClientProperty("backgroundTexture");
            if (backgroundTexture instanceof Icon) {
                JTattooUtilities.fillComponent(g, c, (Icon)backgroundTexture);
            } else {
                g.setColor(c.getBackground());
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        }
    }
}
