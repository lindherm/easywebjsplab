/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.smart;

import com.jtattoo.plaf.BaseToggleButtonUI;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class SmartToggleButtonUI extends BaseToggleButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new SmartToggleButtonUI();
    }

    protected void paintBackground(Graphics g, AbstractButton b) {
        super.paintBackground(g, b);
        if (b.isContentAreaFilled() && b.isRolloverEnabled() && b.getModel().isRollover() && b.isBorderPainted() && (b.getBorder() != null)) {
            g.setColor(SmartLookAndFeel.getFocusColor());
            g.drawLine(1, 1, b.getWidth() - 1, 1);
            g.drawLine(1, 2, b.getWidth() - 1, 2);
            g.drawLine(1, 3, b.getWidth() - 1, 3);
        }
    }
}


