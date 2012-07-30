/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.BasePanelUI;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BernsteinPanelUI extends BasePanelUI {

    private static BernsteinPanelUI panelUI = null;

    public static ComponentUI createUI(JComponent c) {
        if (panelUI == null) {
            panelUI = new BernsteinPanelUI();
        }
        return panelUI;
    }

    public void update(Graphics g, JComponent c) {
        if (c.isOpaque() && c.getBackground() instanceof ColorUIResource && c.getClientProperty("backgroundTexture") == null) {
            BernsteinUtils.fillComponent(g, c);
        } else {
            super.update(g, c);
        }
    }
}
