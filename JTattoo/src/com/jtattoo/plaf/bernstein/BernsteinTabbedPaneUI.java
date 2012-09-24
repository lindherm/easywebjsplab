/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.BaseTabbedPaneUI;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BernsteinTabbedPaneUI extends BaseTabbedPaneUI {

    private static Color SEP_COLORS[] = {
        new Color(229, 187, 0),
        new Color(254, 240, 0),
        new Color(251, 232, 0),
        new Color(247, 225, 0),
        new Color(243, 216, 0),
        new Color(229, 187, 0),};

    public static ComponentUI createUI(JComponent c) {
        return new BernsteinTabbedPaneUI();
    }

    public void installDefaults() {
        super.installDefaults();
        tabAreaInsets.bottom = 6;
    }

    protected boolean isContentOpaque() {
        return false;
    }

    protected Color[] getContentBorderColors(int tabPlacement) {
        return SEP_COLORS;
    }

}