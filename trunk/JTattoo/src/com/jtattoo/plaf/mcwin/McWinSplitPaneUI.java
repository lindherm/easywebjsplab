/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.mcwin;

import com.jtattoo.plaf.BaseSplitPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 * @author Michael Hagen
 */
public class McWinSplitPaneUI extends BaseSplitPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new McWinSplitPaneUI();
    }

    public BasicSplitPaneDivider createDefaultDivider() {
        return new McWinSplitPaneDivider(this);
    }
}
