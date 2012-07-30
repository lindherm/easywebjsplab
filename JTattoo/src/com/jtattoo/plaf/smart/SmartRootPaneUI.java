/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.smart;

import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.plaf.ComponentUI;

/**
 * @author  Michael Hagen
 */
public class SmartRootPaneUI extends BaseRootPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new SmartRootPaneUI();
    }

    public BaseTitlePane createTitlePane(JRootPane root) {
        return new SmartTitlePane(root, this);
    }
}
