/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import java.awt.FontMetrics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;

/**
 * @author  Michael Hagen
 */
public class BaseTableUI extends BasicTableUI {

    public static ComponentUI createUI(JComponent c) {
        return new BaseTableUI();
    }

    public void installDefaults() {
        super.installDefaults();
        // Setup the rowheight. The font may change if UI switches
        FontMetrics fm = table.getFontMetrics(table.getFont());
        table.setRowHeight(fm.getHeight() + (fm.getHeight() / 4));
    }

}
