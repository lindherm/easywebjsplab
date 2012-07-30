/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.BasePanelUI;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class TexturePanelUI extends BasePanelUI {

    private static TexturePanelUI panelUI = null;

    public static ComponentUI createUI(JComponent c) {
        if (panelUI == null) {
            panelUI = new TexturePanelUI();
        }
        return panelUI;
    }

    public void update(Graphics g, JComponent c) {
        if (c.isOpaque() && c.getBackground() instanceof ColorUIResource && c.getClientProperty("backgroundTexture") == null) {
            TextureUtils.fillComponent(g, c, TextureUtils.getTextureType(c));
        } else {
            super.update(g, c);
        }
    }
}
