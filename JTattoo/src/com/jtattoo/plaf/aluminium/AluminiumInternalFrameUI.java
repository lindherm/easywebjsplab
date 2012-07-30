/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.BaseInternalFrameUI;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class AluminiumInternalFrameUI extends BaseInternalFrameUI {

    public AluminiumInternalFrameUI(JInternalFrame b) {
        super(b);
    }

    public static ComponentUI createUI(JComponent c) {
        return new AluminiumInternalFrameUI((JInternalFrame) c);
    }

    protected JComponent createNorthPane(JInternalFrame w) {
        titlePane = new AluminiumInternalFrameTitlePane(w);
        return titlePane;
    }
}

