/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.jtattoo.plaf.graphite;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.text.View;

/**
 * @author Michael Hagen
 */
public class GraphiteTabbedPaneUI extends BaseTabbedPaneUI {

    private Color sepColors[] = null;

    public static ComponentUI createUI(JComponent c) {
        return new GraphiteTabbedPaneUI();
    }

    protected void installComponents() {
        simpleButtonBorder = true;
        super.installComponents();
    }

    protected Color getSelectedBorderColor(int tabIndex) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if (backColor instanceof UIResource) {
            return AbstractLookAndFeel.getControlColorDark();
        } 
        return super.getSelectedBorderColor(tabIndex);
    }

    protected Color getLoBorderColor(int tabIndex) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if ((backColor instanceof UIResource) || (tabIndex == rolloverIndex)) {
            if (tabIndex == tabPane.getSelectedIndex()) {
                return getSelectedBorderColor(tabIndex);
            }
            return ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 30);
        }
        return super.getLoBorderColor(tabIndex);
    }

    protected Color getLoGapBorderColor(int tabIndex) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if (backColor instanceof UIResource) {
            if (tabIndex == tabPane.getSelectedIndex()) {
                return getSelectedBorderColor(tabIndex);
            }
            return ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 30);
        } 
        return ColorHelper.darker(backColor, 20);
    }

    protected Color[] getContentBorderColors(int tabPlacement) {
        if (sepColors == null) {
            sepColors = new Color[5];
            sepColors[0] = getSelectedBorderColor(0);
            sepColors[1] = AbstractLookAndFeel.getControlColorDark();
            sepColors[2] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 4);
            sepColors[3] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 8);
            sepColors[4] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 12);
        }
        return sepColors;
    }

    protected Font getTabFont(boolean isSelected) {
        if (isSelected)
            return super.getTabFont(isSelected).deriveFont(Font.BOLD);
        else
            return super.getTabFont(isSelected);
    }

    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        Color backColor = tabPane.getBackgroundAt(tabIndex);
        if (!(backColor instanceof UIResource)) {
            super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
            return;
        }
        g.setFont(font);
        View v = getTextViewForTab(tabIndex);
        if (v != null) {
            // html
            Graphics2D g2D = (Graphics2D)g;
            Object savedRenderingHint = null;
            if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
                savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
                g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
            }
            v.paint(g, textRect);
            if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
                g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
            }
        } else {
            // plain text
            int mnemIndex = -1;
            if (JTattooUtilities.getJavaVersion() >= 1.4)
                mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
                if (isSelected) {
                    if (ColorHelper.getGrayValue(AbstractLookAndFeel.getControlColorDark()) > 128) {
                        g.setColor(tabPane.getForegroundAt(tabIndex));
                    } else {
                        Color titleColor = AbstractLookAndFeel.getWindowTitleForegroundColor();
                        if (ColorHelper.getGrayValue(titleColor) > 164)
                            g.setColor(Color.black);
                        else
                            g.setColor(Color.white);
                        JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1, textRect.y + 1 + metrics.getAscent());
                        g.setColor(titleColor);
                    }
                } else {
                    g.setColor(tabPane.getForegroundAt(tabIndex));
                }
                JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());

            } else { // tab disabled
                g.setColor(Color.white);
                JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1, textRect.y + metrics.getAscent() + 1);
                g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
                JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
            }
        }
    }

    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if (tabPane.isRequestFocusEnabled() && tabPane.hasFocus() && isSelected && tabIndex >= 0 && textRect.width > 8) {
            g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
            BasicGraphicsUtils.drawDashedRect(g, textRect.x - 4, textRect.y, textRect.width + 8, textRect.height);
            BasicGraphicsUtils.drawDashedRect(g, textRect.x - 3, textRect.y + 1, textRect.width + 6, textRect.height - 2);
        }
    }

}