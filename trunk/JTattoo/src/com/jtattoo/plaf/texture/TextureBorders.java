/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

/**
 * @author Michael Hagen
 */
public class TextureBorders extends BaseBorders {

    private static Border buttonBorder = null;
    private static Border rolloverToolButtonBorder = null;
    private static Border menuItemBorder = null;
    private static Border popupMenuBorder = null;
    private static Border internalFrameBorder = null;
    private static Border toolBarBorder = null;

//------------------------------------------------------------------------------------
// Lazy access methods
//------------------------------------------------------------------------------------
    public static Border getButtonBorder() {
        if (buttonBorder == null) {
            buttonBorder = new ButtonBorder();
        }
        return buttonBorder;
    }

    public static Border getToggleButtonBorder() {
        return getButtonBorder();
    }

    public static Border getRolloverToolButtonBorder() {
        if (rolloverToolButtonBorder == null) {
            rolloverToolButtonBorder = new RolloverToolButtonBorder();
        }
        return rolloverToolButtonBorder;
    }

    public static Border getMenuItemBorder() {
        if (menuItemBorder == null) {
            menuItemBorder = new MenuItemBorder();
        }
        return menuItemBorder;
    }

    public static Border getPopupMenuBorder() {
        if (popupMenuBorder == null) {
            popupMenuBorder = new PopupMenuTextureBorder();
        }
        return popupMenuBorder;
    }

    public static Border getInternalFrameBorder() {
        if (internalFrameBorder == null) {
            internalFrameBorder = new InternalFrameBorder();
        }
        return internalFrameBorder;
    }

    public static Border getToolBarBorder() {
        if (toolBarBorder == null) {
            toolBarBorder = new ToolBarBorder();
        }
        return toolBarBorder;
    }

//------------------------------------------------------------------------------------
// Inner classes
//------------------------------------------------------------------------------------
    public static class ButtonBorder implements Border, UIResource {

        private static final Color defaultColorHi = new Color(220, 230, 245);
        private static final Color defaultColorMed = new Color(212, 224, 243);
        private static final Color defaultColorLo = new Color(200, 215, 240);
        private static final Insets insets = new Insets(3, 4, 3, 4);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2D = (Graphics2D) g;
            AbstractButton b = (AbstractButton) c;
            Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
            if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
                frameColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
            }
            if (!b.isEnabled()) {
                frameColor = ColorHelper.brighter(frameColor, 20);
            } else if (b.getModel().isRollover()) {
                frameColor = ColorHelper.darker(frameColor, 20);
            }
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Composite saveComposite = g2D.getComposite();
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);
            g2D.setComposite(saveComposite);

            if (b.getRootPane() != null && b.equals(b.getRootPane().getDefaultButton()) && !b.hasFocus()) {
                g2D.setColor(ColorHelper.darker(frameColor, 20));
                g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
                if (!b.getModel().isRollover()) {
                    g2D.setColor(defaultColorHi);
                    g2D.drawRoundRect(x + 1, y + 1, w - 3, h - 4, 6, 6);
                    g2D.setColor(defaultColorMed);
                    g2D.drawRoundRect(x + 2, y + 2, w - 5, h - 6, 6, 6);
                    g2D.setColor(defaultColorLo);
                    g2D.drawLine(x + 3, h - 3, w - 3, h - 3);
                    g2D.drawLine(w - 2, y + 4, w - 2, h - 4);
                }
            } else {
                g2D.setColor(frameColor);
                g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
                g2D.setColor(ColorHelper.brighter(frameColor, 20));
                g2D.drawLine(x + 2, y, w - 3, y);
                g2D.setColor(ColorHelper.darker(frameColor, 10));
                g2D.drawLine(x + 2, h - 2, w - 3, h - 2);
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    } // class ButtonBorder

    public static class RolloverToolButtonBorder implements Border, UIResource {

        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color loColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 50);
            if (model.isEnabled()) {
                if ((model.isPressed() && model.isArmed()) || model.isSelected()) {
                    Graphics2D g2D = (Graphics2D) g;
                    Composite composite = g2D.getComposite();
                    g.setColor(loColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
                    g2D.setComposite(alpha);
                    g.setColor(Color.black);
                    g.fillRect(x + 1, y + 1, w - 2, h - 2);
                    g2D.setComposite(composite);
                } else if (model.isRollover()) {
                    Graphics2D g2D = (Graphics2D) g;
                    Composite composite = g2D.getComposite();
                    g.setColor(loColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                    g2D.setComposite(alpha);
                    g.setColor(Color.white);
                    g.fillRect(x + 1, y + 1, w - 2, h - 2);
                    g2D.setComposite(composite);
                }
            }
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    } // class RolloverToolButtonBorder

    public static class PopupMenuTextureBorder extends PopupMenuBorder {

        private static final int shadowSize = 4;
        private static final float shadowAlpha[] = {0.6f, 0.4f, 0.2f, 0.1f};

        public PopupMenuTextureBorder() {
            logoInsets = new Insets(1, 18, shadowSize + 1, shadowSize + 1);
            insets = new Insets(1, 1, shadowSize + 1, shadowSize + 1);
        }

        public boolean hasLogo(Component c) {
            boolean menuBarPopup = false;
            if (c instanceof JPopupMenu) {
                JPopupMenu pm = (JPopupMenu) c;
                if (pm.getInvoker() != null) {
                    menuBarPopup = (pm.getInvoker().getParent() instanceof JMenuBar);
                }
            }
            return (menuBarPopup && (AbstractLookAndFeel.getTheme().getLogoString() != null) && (AbstractLookAndFeel.getTheme().getLogoString().length() > 0));
        }

        public void paintLogo(Component c, Graphics2D g2D, int w, int h) {
            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D imageGraphics = image.createGraphics();

            if (AbstractLookAndFeel.getTheme().isDarkTexture()) {
                TextureUtils.fillComponent(imageGraphics, c, TextureUtils.ROLLOVER_TEXTURE_TYPE);
            } else {
                imageGraphics.setColor(AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor());
                imageGraphics.fillRect(0, 0, w, h);
            }

            imageGraphics.setFont(logoFont);
            FontMetrics fm = imageGraphics.getFontMetrics();
            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.PI + (Math.PI / 2.0));
            imageGraphics.setTransform(at);
            int xs = -h + 6;
            int ys = fm.getAscent();

            String logo = JTattooUtilities.getClippedText(AbstractLookAndFeel.getTheme().getLogoString(), fm, h - 16);

            Color fc = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getMenuSelectionForegroundColor(), 20);
            if (ColorHelper.getGrayValue(fc) < 164) {
                imageGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                imageGraphics.setColor(new Color(220, 220, 220));
                imageGraphics.drawString(logo, xs, ys + 1);
            }
            imageGraphics.setColor(fc);
            imageGraphics.drawString(logo, xs, ys);

            Rectangle2D r2D = new Rectangle2D.Double(0, 0, w, h);
            TexturePaint texturePaint = new TexturePaint(image, r2D);
            g2D.setPaint(texturePaint);
            g2D.fillRect(0, 0, w, h);
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2D = (Graphics2D) g;
            Composite savedComposite = g2D.getComposite();
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            g.setColor(frameColor);
            // Top
            boolean menuBarPopup = false;
            if (c instanceof JPopupMenu) {
                JPopupMenu pm = (JPopupMenu) c;
                if (pm.getInvoker() != null) {
                    menuBarPopup = (pm.getInvoker().getParent() instanceof JMenuBar);
                }
            }
            if (menuBarPopup) {
                if (hasLogo(c)) {
                    g.setFont(c.getFont().deriveFont(Font.BOLD));
                    paintLogo(c, g2D, getBorderInsets(c).left, h - shadowSize);
                    g.setColor(frameColor);
                }
                g.drawLine(x + getBorderInsets(c).left, y, x + w - shadowSize - 1, y);
            } else {
                g.drawLine(x, y, x + w - shadowSize - 1, y);
            }

            // Left
            g.drawLine(x, y, x, y + h - shadowSize - 1);
            // Bottom
            g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
            // Right
            g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);

            // paint the shadow
            g2D.setColor(Color.black);
            for (int i = 0; i < shadowSize; i++) {
                AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, shadowAlpha[i]);
                g2D.setComposite(alpha);
                // bottom
                g.drawLine(x + shadowSize, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
                // right
                g.drawLine(x + w - shadowSize + i, y + shadowSize, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
            }
            g2D.setComposite(savedComposite);
        }
    } // class PopupMenuTextureBorder

    public static class MenuItemBorder extends AbstractBorder implements UIResource {

        private static final Insets insets = new Insets(2, 2, 2, 2);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            JMenuItem b = (JMenuItem) c;
            ButtonModel model = b.getModel();
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            if (c.getParent() instanceof JMenuBar) {
                if (model.isArmed() || model.isSelected()) {
                    g.setColor(frameColor);
                    g.drawLine(x, y, x + w - 1, y);
                    g.drawLine(x, y, x, y + h - 1);
                    g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
                }
            } else {
                if (model.isArmed() || (c instanceof JMenu && model.isSelected())) {
                    g.setColor(frameColor);
                    g.drawLine(x, y, x + w - 1, y);
                    g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
                }
            }
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return false;
        }
    } // class MenuItemBorder

    public static class InternalFrameBorder extends BaseInternalFrameBorder {

        private static final Color FRAME_BORDER_COLOR = new Color(128, 128, 128);
        private static final Color FRAME_COLORS[] = new Color[]{new Color(144, 144, 144), new Color(180, 180, 180), new Color(216, 216, 216), new Color(236, 236, 236), new Color(164, 164, 164), new Color(196, 196, 196), new Color(184, 184, 184), new Color(172, 172, 172)};

        public Insets getBorderInsets(Component c) {
            if (isResizable(c)) {
                return new Insets(5, 8, 6, 8);
            } else {
                return new Insets(paletteInsets.top, paletteInsets.left, paletteInsets.bottom, paletteInsets.right);
            }
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            boolean isJFrameBorder = false;
            if (c instanceof JRootPane) {
                JRootPane jp = (JRootPane) c;
                if (jp.getParent() instanceof JFrame) {
                    isJFrameBorder = true;
                }
            }
            Graphics2D g2D = (Graphics2D) g;
            Composite savedComposite = g2D.getComposite();
//            if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
//                }
//                g.drawRect(0, 0, w - 1, h - 1);
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowTitleBackgroundColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveTitleBackgroundColor());
//                }
//                g.drawRect(1, 1, w - 3, h - 3);
//                g.drawRect(2, 2, w - 5, h - 5);
//                g.drawRect(3, 3, w - 7, h - 7);
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
//                }
//                g.drawRect(4, 4, w - 9, h - 9);
//                return;
//            } else
            if (!isJFrameBorder) {
                TextureUtils.fillComponent(g, c, 1, 1, w - 1, h - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
                g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
                g.drawRect(0, 0, w - 1, h - 1);
            } else {
                Insets bi = getBorderInsets(c);
                Color frameColor = AbstractLookAndFeel.getTheme().getWindowBorderColor();
                // top
                g.setColor(frameColor);
                g.drawLine(x, y, w, y);
                TextureUtils.fillComponent(g, c, 1, 1, w, bi.top - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
                // bottom
                g.setColor(frameColor);
                g.drawLine(x, y + h - 1, w, y + h - 1);
                TextureUtils.fillComponent(g, c, 1, h - bi.bottom, w, bi.bottom - 1, TextureUtils.WINDOW_TEXTURE_TYPE);

                g.setColor(FRAME_BORDER_COLOR);
                g.drawLine(x, y, x, y + h);
                g.drawLine(x + w - 1, y, x + w - 1, y + h);
                // left
                for (int i = 1; i < FRAME_COLORS.length; i++) {
                    g2D.setColor(FRAME_COLORS[i]);
                    g2D.drawLine(i, 0, i, h);
                }
                // right
                for (int i = 0; i < FRAME_COLORS.length - 1; i++) {
                    g2D.setColor(FRAME_COLORS[i]);
                    g2D.drawLine(w - 8 + i, 0, w - 8 + i, h);
                }
                g.setColor(ColorHelper.brighter(FRAME_BORDER_COLOR, 20));
                g.drawLine(x, y, x + bi.left - 1, y);
                g.drawLine(x + w - bi.right, y, x + w - 1, y);

            } // JFrame border
            if (isResizable(c)) {
                // top
                float alphaValue = 0.4f;
                float alphaDelta = 0.1f;
                g2D.setColor(Color.white);
                if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
                    alphaValue = 0.8f;
                    alphaDelta = 0.2f;
                }
                for (int i = 1; i < 5; i++) {
                    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
                    g2D.drawLine(1, i, w - 2, i);
                    alphaValue -= alphaDelta;
                }
                // bottom
                alphaValue = 0.3f;
                alphaDelta = 0.05f;
                g2D.setColor(Color.black);
                if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
                    alphaValue = 0.14f;
                    alphaDelta = 0.02f;
                }
                for (int i = 1; i < 6; i++) {
                    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
                    g2D.drawLine(1, h - i, w - 2, h - i);
                    alphaValue -= alphaDelta;
                }
            }
            g2D.setComposite(savedComposite);
        }
    } // class InternalFrameBorder
    
    public static class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

        private static final LazyImageIcon HOR_RUBBER_ICON = new LazyImageIcon("texture/icons/HorRubber.gif");
        private static final LazyImageIcon VER_RUBBER_ICON = new LazyImageIcon("texture/icons/VerRubber.gif");

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (((JToolBar) c).isFloatable()) {
                if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                    int x1 = 4;
                    int y1 = (h - HOR_RUBBER_ICON.getIconHeight()) / 2;
                    HOR_RUBBER_ICON.paintIcon(c, g, x1, y1);
                } else {
                    int x1 = (w - VER_RUBBER_ICON.getIconWidth()) / 2 + 2;
                    int y1 = 4;
                    VER_RUBBER_ICON.paintIcon(c, g, x1, y1);
                }
            }
        }

        public Insets getBorderInsets(Component c) {
            Insets insets = new Insets(2, 2, 2, 2);
            if (((JToolBar) c).isFloatable()) {
                if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                    if (JTattooUtilities.isLeftToRight(c)) {
                        insets.left = 15;
                    } else {
                        insets.right = 15;
                    }
                } else {
                    insets.top = 15;
                }
            }
            Insets margin = ((JToolBar) c).getMargin();
            if (margin != null) {
                insets.left += margin.left;
                insets.top += margin.top;
                insets.right += margin.right;
                insets.bottom += margin.bottom;
            }
            return insets;
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            Insets insets = getBorderInsets(c);
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

    } // class ToolBarBorder
    
} // class TextureBorders

