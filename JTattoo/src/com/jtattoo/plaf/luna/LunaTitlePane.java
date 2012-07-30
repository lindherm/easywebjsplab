/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.JRootPane;

/**
 * @author  Michael Hagen
 */
public class LunaTitlePane extends BaseTitlePane {

    public LunaTitlePane(JRootPane root, BaseRootPaneUI ui) {
        super(root, ui);
    }

    protected int getHorSpacing() {
        return 2;
    }

    protected int getVerSpacing() {
        return 5;
    }

    public void createButtons() {
        iconifyButton = new BaseTitleButton(iconifyAction, ICONIFY, iconifyIcon, 1.0f);
        maxButton = new BaseTitleButton(restoreAction, MAXIMIZE, maximizeIcon, 1.0f);
        closeButton = new BaseTitleButton(closeAction, CLOSE, closeIcon, 1.0f);
    }

    public void paintBorder(Graphics g) {
        if (isActive()) {
            g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
        } else {
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 40));
        }
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    public void paintText(Graphics g, int x, int y, String title) {
        if (isActive()) {
            g.setColor(ColorHelper.brighter(LunaLookAndFeel.getTheme().getWindowBorderColor(), 10));
            JTattooUtilities.drawString(rootPane, g, title, x - 1, y - 1);
            g.setColor(ColorHelper.darker(LunaLookAndFeel.getTheme().getWindowBorderColor(), 25));
            JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
            g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
        } else {
            g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
        }
        JTattooUtilities.drawString(rootPane, g, title, x, y);
    }
    
}
