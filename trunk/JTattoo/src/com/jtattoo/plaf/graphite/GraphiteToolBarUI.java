/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.graphite;

import com.jtattoo.plaf.AbstractToolBarUI;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class GraphiteToolBarUI extends AbstractToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new GraphiteToolBarUI();
    }

    public Border getRolloverBorder() {
        return GraphiteBorders.getRolloverToolButtonBorder();
    }

    public Border getNonRolloverBorder() {
        return GraphiteBorders.getToolButtonBorder();
    }

    public boolean isButtonOpaque() {
        return false;
    }
}
