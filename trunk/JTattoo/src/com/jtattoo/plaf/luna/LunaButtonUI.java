/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * @author Michael Hagen
 */
public class LunaButtonUI extends BaseButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new LunaButtonUI();
    }

    protected void paintBackground(Graphics g, AbstractButton b) {
        int w = b.getWidth();
        int h = b.getHeight();
        Graphics2D g2D = (Graphics2D) g;
        Shape savedClip = g.getClip();
        if ((b.getBorder() != null) && b.isBorderPainted() && (b.getBorder() instanceof UIResource)) {
            Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, 6, 6));
            clipArea.intersect(new Area(savedClip));
            g2D.setClip(clipArea);
        }
        super.paintBackground(g, b);
        if (b.isContentAreaFilled() && b.isRolloverEnabled() && b.getModel().isRollover() && (b.getBorder() != null) && b.isBorderPainted()) {
            g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
            Insets ins = b.getBorder().getBorderInsets(b);
            if ((ins.top == 0) && (ins.left == 1)) {
                g.drawRect(1, 0, w - 2, h - 1);
                g.drawRect(2, 1, w - 4, h - 3);
            } else {
                g.drawRect(1, 1, w - 4, h - 4);
                g.drawRect(2, 2, w - 6, h - 6);
            }
        }
        g2D.setClip(savedClip);
    }

    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        g.setColor(Color.black);
        BasicGraphicsUtils.drawDashedRect(g, 3, 3, b.getWidth() - 6, b.getHeight() - 6);
    }
}
