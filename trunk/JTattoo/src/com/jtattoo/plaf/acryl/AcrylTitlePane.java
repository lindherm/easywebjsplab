/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.acryl;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * @author  Michael Hagen
 */
public class AcrylTitlePane extends BaseTitlePane {

    public AcrylTitlePane(JRootPane root, BaseRootPaneUI ui) {
        super(root, ui);
    }

    public LayoutManager createLayout() {
        return new TitlePaneLayout();
    }

    protected int getHorSpacing() {
        return 1;
    }

    protected int getVerSpacing() {
        return 3;
    }

    public void paintBorder(Graphics g) {
        if (isActive()) {
            g.setColor(AcrylLookAndFeel.getWindowBorderColor());
        } else {
            g.setColor(AcrylLookAndFeel.getWindowInactiveBorderColor());
        }
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    public void paintText(Graphics g, int x, int y, String title) {
        Color shadowColor = AcrylLookAndFeel.getWindowTitleColorDark();
        if (isActive()) {
            shadowColor = ColorHelper.darker(shadowColor, 30);
        }
        g.setColor(shadowColor);
        JTattooUtilities.drawString(rootPane, g, title, x - 1, y - 1);
        JTattooUtilities.drawString(rootPane, g, title, x - 1, y + 1);
        JTattooUtilities.drawString(rootPane, g, title, x + 1, y - 1);
        JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
        if (isActive()) {
            g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
        } else {
            g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
        }
        JTattooUtilities.drawString(rootPane, g, title, x, y);
    }
//-----------------------------------------------------------------------------------------------    
    protected class TitlePaneLayout implements LayoutManager {

        public void addLayoutComponent(String name, Component c) {
        }

        public void removeLayoutComponent(Component c) {
        }

        public Dimension preferredLayoutSize(Container c) {
            int height = computeHeight();
            return new Dimension(height, height);
        }

        public Dimension minimumLayoutSize(Container c) {
            return preferredLayoutSize(c);
        }

        protected int computeHeight() {
            FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
            return fm.getHeight() + 6;
        }

        public void layoutContainer(Container c) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                layoutMacStyle(c);
            } else {
                layoutDefault(c);
            }
        }
        
        public void layoutDefault(Container c) {
            boolean leftToRight = isLeftToRight();

            int spacing = getHorSpacing();
            int w = getWidth();
            int h = getHeight();

            // assumes all buttons have the same dimensions these dimensions include the borders
            int buttonHeight = h - getVerSpacing();
            int buttonWidth = buttonHeight + 10;

            int x = leftToRight ? spacing : w - buttonWidth - spacing;
            int y = Math.max(0, ((h - buttonHeight) / 2) - 1);
            int cpx = 0;
            int cpy = 0;
            int cpw = getWidth();
            int cph = getHeight();

            if (menuBar != null) {
                int mw = menuBar.getPreferredSize().width;
                int mh = menuBar.getPreferredSize().height;
                if (leftToRight) {
                    cpx = 4 + mw;
                    menuBar.setBounds(2, (h - mh) / 2, mw, mh);
                } else {
                    menuBar.setBounds(getWidth() - mw, (h - mh) / 2, mw, mh);
                }
                cpw -= 4 + mw;
            }

            x = leftToRight ? w - spacing : 0;
            if (closeButton != null) {
                x += leftToRight ? -buttonWidth : spacing;
                closeButton.setBounds(x, y, buttonWidth, buttonHeight);
                if (!leftToRight) {
                    x += buttonWidth;
                }
            }

            if ((maxButton != null) && (maxButton.getParent() != null)) {
                if (DecorationHelper.isFrameStateSupported(Toolkit.getDefaultToolkit(), BaseRootPaneUI.MAXIMIZED_BOTH)) {
                    x += leftToRight ? -spacing - buttonWidth : spacing;
                    maxButton.setBounds(x, y, buttonWidth, buttonHeight);
                    if (!leftToRight) {
                        x += buttonWidth;
                    }
                }
            }

            if ((iconifyButton != null) && (iconifyButton.getParent() != null)) {
                x += leftToRight ? -spacing - buttonWidth : spacing;
                iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
                if (!leftToRight) {
                    x += buttonWidth;
                }
            }
            buttonsWidth = leftToRight ? w - x : x;
            if (customTitlePanel != null) {
                if (!leftToRight) {
                    cpx += buttonsWidth;
                }
                cpw -= buttonsWidth;
                Graphics g = getGraphics();
                if (g != null) {
                    FontMetrics fm = g.getFontMetrics();
                    int tw = SwingUtilities.computeStringWidth(fm, JTattooUtilities.getClippedText(getTitle(), fm, cpw));
                    if (leftToRight) {
                        cpx += tw;
                    }
                    cpw -= tw;
                }
                customTitlePanel.setBounds(cpx, cpy, cpw, cph);
            }
        }
        
        private void layoutMacStyle(Container c) {
            int spacing = getHorSpacing();
            int w = getWidth();
            int h = getHeight();

            // assumes all buttons have the same dimensions these dimensions include the borders
            int buttonHeight = h - getVerSpacing();
            int buttonWidth = buttonHeight + 10;

            int x = 0;
            int y = Math.max(0, ((h - buttonHeight) / 2) - 1);

            if (closeButton != null) {
                closeButton.setBounds(x, y, buttonWidth, buttonHeight);
                x += spacing + buttonWidth;
            }
            if ((maxButton != null) && (maxButton.getParent() != null)) {
                if (DecorationHelper.isFrameStateSupported(Toolkit.getDefaultToolkit(), BaseRootPaneUI.MAXIMIZED_BOTH)) {
                    maxButton.setBounds(x, y, buttonWidth, buttonHeight);
                    x += spacing + buttonWidth;
                }
            }
            if ((iconifyButton != null) && (iconifyButton.getParent() != null)) {
                iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
                x += spacing + buttonWidth;
            }

            buttonsWidth = x;
            
            int cpx = 0;
            int cpy = 0;
            int cpw = w;
            int cph = h;
            if (menuBar != null) {
                int mw = menuBar.getPreferredSize().width;
                int mh = menuBar.getPreferredSize().height;
                menuBar.setBounds(w - mw, (h - mh) / 2, mw, mh);
                cpw -= 4 + mw;
            }
            
            if (customTitlePanel != null) {
                cpx += buttonsWidth;
                cpw -= buttonsWidth;
                Graphics g = getGraphics();
                if (g != null) {
                    FontMetrics fm = g.getFontMetrics();
                    int tw = SwingUtilities.computeStringWidth(fm, JTattooUtilities.getClippedText(getTitle(), fm, cpw));
                    cpx += tw;
                    cpw -= tw;
                }
                customTitlePanel.setBounds(cpx, cpy, cpw, cph);
            }
        }
        
    }
}
