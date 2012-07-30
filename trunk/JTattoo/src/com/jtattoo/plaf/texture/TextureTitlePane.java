/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.JRootPane;

/**
 * @author  Michael Hagen
 */
public class TextureTitlePane extends BaseTitlePane {
    
    public TextureTitlePane(JRootPane root, BaseRootPaneUI ui) {
        super(root, ui); 
    }

    protected int getHorSpacing() {
        return 0;
    }
    
    protected int getVerSpacing() {
        return 0;
    }

    public void paintBorder(Graphics g) {
    }

    public void paintBackground(Graphics g) {
        TextureUtils.fillComponent(g, this, TextureUtils.WINDOW_TEXTURE_TYPE);
    }

    public void paintText(Graphics g, int x, int y, String title) {
        Graphics2D g2D = (Graphics2D)g;
        Shape savedClip = g2D.getClip();
        Color fc = AbstractLookAndFeel.getWindowTitleForegroundColor();
        if (fc.equals(Color.white)) {
            Color bc = AbstractLookAndFeel.getWindowTitleColorDark();
            g2D.setColor(bc);
            JTattooUtilities.drawString(rootPane, g, title, x-1, y-1);
            g2D.setColor(ColorHelper.darker(bc, 30));
            JTattooUtilities.drawString(rootPane, g, title, x+1, y+1);
        }
        g.setColor(fc);

        Area clipArea = new Area(new Rectangle2D.Double(x, 0, getWidth(), getHeight() / 2));
        clipArea.intersect(new Area(savedClip));
        g2D.setClip(clipArea);
        JTattooUtilities.drawString(rootPane, g, title, x, y);

        clipArea = new Area(new Rectangle2D.Double(x, (getHeight() / 2), getWidth(), getHeight()));
        clipArea.intersect(new Area(savedClip));
        g2D.setClip(clipArea);
        g.setColor(ColorHelper.darker(fc, 20));
        JTattooUtilities.drawString(rootPane, g, title, x, y);

        g2D.setClip(savedClip);
    }
    
}
