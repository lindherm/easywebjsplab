/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.BaseScrollPaneUI;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class TextureScrollPaneUI extends BaseScrollPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new TextureScrollPaneUI();
    }

    public void installDefaults(JScrollPane p) {
        super.installDefaults(p);
        p.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    }

    public void update(Graphics g, JComponent c) {
        if (c.getBackground() instanceof ColorUIResource) {
            if (c.isOpaque()) {
                TextureUtils.fillComponent(g, c, TextureUtils.getTextureType(c));
            }
        } else {
            super.update(g, c);
        }
    }

}
