/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.smart;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

/**
 * author Michael Hagen
 */
public class SmartTabbedPaneUI extends BaseTabbedPaneUI {

    private static final Color SELECTED_TAB_COLORS[] = {AbstractLookAndFeel.getBackgroundColor()};

    public static ComponentUI createUI(JComponent c) {
        return new SmartTabbedPaneUI();
    }

    public void installDefaults() {
        super.installDefaults();
        tabAreaInsets = new Insets(2, 6, 2, 6);
        contentBorderInsets = new Insets(1, 1, 0, 0);
    }

    protected Font getTabFont(boolean isSelected) {
        if (isSelected) {
            return super.getTabFont(isSelected).deriveFont(Font.BOLD);
        } else {
            return super.getTabFont(isSelected);
        }
    }

    protected Color[] getTabColors(int tabIndex, boolean isSelected) {
        if (isSelected) {
            return SELECTED_TAB_COLORS;
        } else {
            return super.getTabColors(tabIndex, isSelected);
        }
    }

    protected Color getGapColor(int tabIndex) {
        if (tabIndex == tabPane.getSelectedIndex()) {
            return AbstractLookAndFeel.getBackgroundColor();
        }
        return super.getGapColor(tabIndex);
    }

    protected boolean hasInnerBorder() {
        return true;
    }

    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if (isSelected) {
            if (tabPane.getBackgroundAt(tabIndex) instanceof UIResource) {
                g.setColor(AbstractLookAndFeel.getBackgroundColor());
            } else {
                g.setColor(tabPane.getBackgroundAt(tabIndex));
            }
            if (tabPlacement == TOP) {
                g.fillRect(x + 1, y + 1, w - 1, h + 2);
            } else if (tabPlacement == LEFT) {
                g.fillRect(x + 1, y + 1, w + 2, h - 1);
            } else if (tabPlacement == BOTTOM) {
                g.fillRect(x + 1, y - 2, w - 1, h + 1);
            } else {
                g.fillRect(x - 2, y + 1, w + 2, h - 1);
            }
        } else {
            super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
            switch (tabPlacement) {
                case TOP: {
                    if (tabIndex == rolloverIndex) {
                        g.setColor(AbstractLookAndFeel.getFocusColor());
                        g.fillRect(x + 2, y + 1, w - 3, 2);
                    }
                    break;
                }
                case LEFT: {
                    if (tabIndex == rolloverIndex) {
                        g.setColor(AbstractLookAndFeel.getFocusColor());
                        g.fillRect(x, y + 2, w - 1, 2);
                    }
                    break;
                }
                case RIGHT: {
                    if (tabIndex == rolloverIndex) {
                        g.setColor(AbstractLookAndFeel.getFocusColor());
                        g.fillRect(x, y + 2, w - 1, 2);
                    }
                    break;
                }
                case BOTTOM: {
                    if (tabIndex == rolloverIndex) {
                        g.setColor(AbstractLookAndFeel.getFocusColor());
                        g.fillRect(x + 2, y + h - 3, w - 3, 2);
                    }
                    break;
                }
            }
        }
    }

}