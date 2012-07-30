/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.BaseInternalFrameUI;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class FastInternalFrameUI extends BaseInternalFrameUI {

    public FastInternalFrameUI(JInternalFrame b) {
        super(b);
    }

    public static ComponentUI createUI(JComponent c) {
        return new FastInternalFrameUI((JInternalFrame) c);
    }

    protected JComponent createNorthPane(JInternalFrame w) {
        titlePane = new FastInternalFrameTitlePane(w);
        return titlePane;
    }
}

