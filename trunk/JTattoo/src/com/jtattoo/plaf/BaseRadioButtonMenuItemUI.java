/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BaseRadioButtonMenuItemUI extends BaseMenuItemUI {

    public static ComponentUI createUI(JComponent b) {
        return new BaseRadioButtonMenuItemUI();
    }

    protected void installDefaults() {
        super.installDefaults();
        checkIcon = UIManager.getIcon("RadioButtonMenuItem.checkIcon");
    }

}
