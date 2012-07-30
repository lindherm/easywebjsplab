/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JInternalFrame;

/**
 * @author Michael Hagen
 */
public class MintInternalFrameTitlePane extends BaseInternalFrameTitlePane {

    public MintInternalFrameTitlePane(JInternalFrame f) {
        super(f);
    }

    public void paintPalette(Graphics g) {
        if (JTattooUtilities.isFrameActive(this)) {
            JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, getWidth(), getHeight());
        } else {
            JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0, getWidth(), getHeight());
        }
    }

    public void paintBackground(Graphics g) {
        if (JTattooUtilities.isActive(this)) {
            setBackground(MintLookAndFeel.getTheme().getWindowTitleBackgroundColor());
            JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, getWidth(), getHeight());
        } else {
            setBackground(MintLookAndFeel.getTheme().getWindowInactiveTitleBackgroundColor());
            JTattooUtilities.fillVerGradient(g, MintLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0, getWidth(), getHeight());
        }
    }

    public void paintBorder(Graphics g) {
        g.setColor(ColorHelper.darker(MintLookAndFeel.getTheme().getWindowTitleColorDark(), 10));
        g.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
        g.setColor(Color.white);
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
