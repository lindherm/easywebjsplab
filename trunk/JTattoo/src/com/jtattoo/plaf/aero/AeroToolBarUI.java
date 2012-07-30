/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.aero;

import com.jtattoo.plaf.AbstractToolBarUI;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class AeroToolBarUI extends AbstractToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new AeroToolBarUI();
    }

    public Border getRolloverBorder() {
        return AeroBorders.getRolloverToolButtonBorder();
    }

    public Border getNonRolloverBorder() {
        return AeroBorders.getToolButtonBorder();
    }

    public boolean isButtonOpaque() {
        return false;
    }
}
