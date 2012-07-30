/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseSplitPaneDivider;
import java.awt.Graphics;

/**
 * @author Michael Hagen
 */
public class FastSplitPaneDivider extends BaseSplitPaneDivider {

    public FastSplitPaneDivider(FastSplitPaneUI ui) {
        super(ui);
    }

    public void paint(Graphics g) {
        g.setColor(AbstractLookAndFeel.getBackgroundColor());
        g.fillRect(0, 0, getSize().width, getSize().height);
        paintComponents(g);
    }
}
