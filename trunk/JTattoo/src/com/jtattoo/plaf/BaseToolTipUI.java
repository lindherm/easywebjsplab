/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * @author Michael Hagen
 */
public class BaseToolTipUI extends MetalToolTipUI {

    public static ComponentUI createUI(JComponent c) {
        return new BaseToolTipUI();
    }
}
