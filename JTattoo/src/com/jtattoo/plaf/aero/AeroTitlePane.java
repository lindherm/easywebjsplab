/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.jtattoo.plaf.aero;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/**
 * @author  Michael Hagen
 */
public class AeroTitlePane extends BaseTitlePane {
    
    public AeroTitlePane(JRootPane root, BaseRootPaneUI ui) { 
        super(root, ui); 
    }

    protected int getHorSpacing() {
        return 0;
    }
    
    protected int getVerSpacing() {
        return 0;
    }
    
    public void createButtons() {
        iconifyButton = new TitleButton(iconifyAction, ICONIFY, iconifyIcon);
        maxButton = new TitleButton(restoreAction, MAXIMIZE, maximizeIcon);
        closeButton = new TitleButton(closeAction, CLOSE, closeIcon);
    }
    
    public void paintBorder(Graphics g) {
        if (isActive())
            g.setColor(ColorHelper.brighter(AeroLookAndFeel.getWindowTitleColorDark(), 50));
        else
            g.setColor(ColorHelper.darker(AeroLookAndFeel.getWindowInactiveTitleColorDark(), 10));
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
    
    public void paintText(Graphics g, int x, int y, String title) {
        if (isActive()) {
            Color titleColor = AbstractLookAndFeel.getWindowTitleForegroundColor();
            if (ColorHelper.getGrayValue(titleColor) > 164)
                g.setColor(Color.black);
            else
                g.setColor(Color.white);
            JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
            g.setColor(titleColor);
            JTattooUtilities.drawString(rootPane, g, title, x, y);
        }
        else {
            g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
            JTattooUtilities.drawString(rootPane, g, title, x, y);
        }
    }
    
//------------------------------------------------------------------------------    
    private class TitleButton extends BaseTitleButton {
        
        public TitleButton(Action action, String accessibleName, Icon icon) {
            super(action, accessibleName, icon, 1.0f);
        }

        public void paint(Graphics g) {
            boolean isPressed = getModel().isPressed();
            boolean isArmed = getModel().isArmed();
            boolean isRollover = getModel().isRollover();
            int width = getWidth();
            int height = getHeight();
            Color colors[] = AeroLookAndFeel.getTheme().getButtonColors();
            if (isRollover)
                colors = AeroLookAndFeel.getTheme().getRolloverColors();
            if (isPressed && isArmed)
                colors = AeroLookAndFeel.getTheme().getPressedColors();
            JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
            
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                g.setColor(Color.lightGray);
                g.drawLine(width - 1, 0, width - 1, height);
                g.drawLine(0, height - 1, width, height - 1);
                g.setColor(Color.white);
                g.drawLine(0, 0, 0, height - 2);
            } else {
                g.setColor(Color.lightGray);
                g.drawLine(0, 0, 0, height);
                g.drawLine(0, height - 1, width, height - 1);
                g.setColor(Color.white);
                g.drawLine(1, 0, 1, height - 2);
            }
            getIcon().paintIcon(this, g, 1, 0);
        }
    }
}
