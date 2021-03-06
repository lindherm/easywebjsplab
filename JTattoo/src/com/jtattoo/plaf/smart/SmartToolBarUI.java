/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.smart;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

public class SmartToolBarUI extends AbstractToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new SmartToolBarUI();
    }

    public Border getRolloverBorder() {
        return SmartBorders.getRolloverToolButtonBorder();
    }

    public Border getNonRolloverBorder() {
        return SmartBorders.getToolButtonBorder();
    }

    public boolean isButtonOpaque() {
        return false;
    }

    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        JTattooUtilities.fillHorGradient(g, SmartLookAndFeel.getTheme().getToolBarColors(), 0, 0, w, h - 2);
        if ((toolBar.getOrientation() == JToolBar.HORIZONTAL) && isToolbarDecorated() && isToolBarUnderMenubar()) {
            g.setColor(Color.white);
            g.drawLine(0, 0, w, 0);
            g.drawLine(0, h - 2, w, h - 2);
            g.setColor(ColorHelper.darker(SmartLookAndFeel.getToolbarColorDark(), 10));
            g.drawLine(0, 1, w, 1);
            g.drawLine(0, h - 1, w, h - 1);
        }
    }
}
