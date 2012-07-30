/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JInternalFrame;

/**
 * @author Michael Hagen
 */
public class LunaInternalFrameTitlePane extends BaseInternalFrameTitlePane {

    private static final Color frameColor = new Color(0, 25, 207);

    public LunaInternalFrameTitlePane(JInternalFrame f) {
        super(f);
    }

    protected int getHorSpacing() {
        return 2;
    }

    protected int getVerSpacing() {
        return 5;
    }

    public void paintText(Graphics g, int x, int y, String title) {
        if (isActive()) {
            g.setColor(ColorHelper.brighter(LunaLookAndFeel.getTheme().getWindowBorderColor(), 10));
            JTattooUtilities.drawString(frame, g, title, x - 1, y - 1);
            g.setColor(ColorHelper.darker(LunaLookAndFeel.getTheme().getWindowBorderColor(), 25));
            JTattooUtilities.drawString(frame, g, title, x + 1, y + 1);
            g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
        } else {
            g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
        }
        JTattooUtilities.drawString(frame, g, title, x, y);
    }

    public void paintBorder(Graphics g) {
        if (!JTattooUtilities.isActive(this)) {
            g.setColor(ColorHelper.brighter(frameColor, 20));
        } else {
            g.setColor(frameColor);
        }
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
