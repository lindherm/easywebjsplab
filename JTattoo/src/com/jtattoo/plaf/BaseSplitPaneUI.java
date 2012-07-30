/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @author Michael Hagen
 */
public class BaseSplitPaneUI extends BasicSplitPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new BaseSplitPaneUI();
    }

    public BasicSplitPaneDivider createDefaultDivider() {
        return new BaseSplitPaneDivider(this);
    }
}
