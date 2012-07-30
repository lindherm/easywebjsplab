/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.AbstractToolBarUI;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class FastToolBarUI extends AbstractToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new FastToolBarUI();
    }

    public Border getRolloverBorder() {
        return FastBorders.getRolloverToolButtonBorder();
    }

    public Border getNonRolloverBorder() {
        return FastBorders.getToolButtonBorder();
    }

    public boolean isButtonOpaque() {
        return false;
    }
}

