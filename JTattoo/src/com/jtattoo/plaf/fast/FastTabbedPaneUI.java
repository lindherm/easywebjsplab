/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

/**
 * author Michael Hagen
 */
public class FastTabbedPaneUI extends BaseTabbedPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new FastTabbedPaneUI();
    }

    public void installDefaults() {
        super.installDefaults();
        roundedTabs = false;
        tabAreaInsets = new Insets(2, 6, 2, 6);
        contentBorderInsets = new Insets(0, 0, 0, 0);
    }

    protected Color getLoBorderColor(int tabIndex) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if (backColor instanceof UIResource) {
            return AbstractLookAndFeel.getControlDarkShadow();
        } else {
            return ColorHelper.darker(backColor, 20);
        }
    }
    
    protected Color getHiBorderColor(int tabIndex) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if (backColor instanceof UIResource) {
            return AbstractLookAndFeel.getControlHighlight();
        } else {
            return ColorHelper.brighter(backColor, 20);
        }
    }
    
    protected Color getGapColor(int tabIndex) {
        if (tabIndex == tabPane.getSelectedIndex() && (tabPane.getBackgroundAt(tabIndex) instanceof UIResource)) {
             return AbstractLookAndFeel.getTheme().getBackgroundColor();
        } else {
            if ((tabIndex >= 0) && (tabIndex < tabPane.getTabCount())) {
                return tabPane.getBackgroundAt(tabIndex);
            }
        }
        return tabAreaBackground;
    }

    protected boolean hasInnerBorder() {
        return true;
    }

    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if (isTabOpaque() || isSelected) {
            if (isSelected && (tabPane.getBackgroundAt(tabIndex) instanceof UIResource)) {
                g.setColor(AbstractLookAndFeel.getTheme().getBackgroundColor());
            } else {
                g.setColor(tabPane.getBackgroundAt(tabIndex));
            }
            switch (tabPlacement) {
                case TOP:
                    if (isSelected) {
                        g.fillRect(x + 1, y + 1, w - 1, h + 2);
                    } else {
                        g.fillRect(x + 1, y + 1, w - 1, h - 1);
                    }
                    break;
                case LEFT:
                    if (isSelected) {
                        g.fillRect(x + 1, y + 1, w + 2, h - 1);
                    } else {
                        g.fillRect(x + 1, y + 1, w - 1, h - 1);
                    }
                    break;
                case BOTTOM:
                    if (isSelected) {
                        g.fillRect(x + 1, y - 2, w - 1, h + 1);
                    } else {
                        g.fillRect(x + 1, y, w - 1, h - 1);
                    }
                    break;
                case RIGHT:
                    if (isSelected) {
                        g.fillRect(x - 2, y + 1, w + 2, h - 1);
                    } else {
                        g.fillRect(x, y + 1, w, h - 1);
                    }
                    break;
            }
        }
    }

}