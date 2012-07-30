/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.AbstractToolBarUI;
import com.jtattoo.plaf.JTattooUtilities;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class MintToolBarUI extends AbstractToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new MintToolBarUI();
    }

    public Border getRolloverBorder() {
        return MintBorders.getRolloverToolButtonBorder();
    }

    public Border getNonRolloverBorder() {
        return MintBorders.getToolButtonBorder();
    }

    public boolean isButtonOpaque() {
        return false;
    }

    public void paint(Graphics g, JComponent c) {
        JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getToolBarColors(), 0, 0, c.getWidth(), c.getHeight());
    }
}

