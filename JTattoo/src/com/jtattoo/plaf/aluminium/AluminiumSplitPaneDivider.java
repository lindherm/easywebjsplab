/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.BaseSplitPaneDivider;
import java.awt.*;

/**
 * @author Michael Hagen
 */
public class AluminiumSplitPaneDivider extends BaseSplitPaneDivider {

    public AluminiumSplitPaneDivider(AluminiumSplitPaneUI ui) {
        super(ui);
    }

    public void paint(Graphics g) {
        AluminiumUtils.fillComponent(g, this);
        Graphics2D g2D = (Graphics2D) g;
        Composite composite = g2D.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2D.setComposite(alpha);
        super.paint(g);
        g2D.setComposite(composite);
    }
}
