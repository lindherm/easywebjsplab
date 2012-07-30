/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.BasePanelUI;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class AluminiumPanelUI extends BasePanelUI {

    private static AluminiumPanelUI panelUI = null;

    public static ComponentUI createUI(JComponent c) {
        if (panelUI == null) {
            panelUI = new AluminiumPanelUI();
        }
        return panelUI;
    }

    public void update(Graphics g, JComponent c) {
        if (c.isOpaque() && c.getBackground() instanceof ColorUIResource && c.getClientProperty("backgroundTexture") == null) {
            AluminiumUtils.fillComponent(g, c);
        } else {
            super.update(g, c);
        }
    }
}
