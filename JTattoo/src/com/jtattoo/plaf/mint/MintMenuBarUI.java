/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.JTattooUtilities;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * @author Michael Hagen
 */
public class MintMenuBarUI extends BasicMenuBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new MintMenuBarUI();
    }

    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getMenuBarColors(), 0, 0, w, h);
    }
}