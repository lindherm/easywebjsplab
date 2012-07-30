/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.BaseTableHeaderUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author  Michael Hagen
 */
public class LunaTableHeaderUI extends BaseTableHeaderUI {

    public static ComponentUI createUI(JComponent c) {
        return new LunaTableHeaderUI();
    }

    protected boolean drawRolloverBar() {
        return true;
    }
    
}
