/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.hifi;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;


/**
 * @author Michael Hagen
 */
public class HiFiMenuBarUI extends BasicMenuBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new HiFiMenuBarUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        if ((c != null) && (c instanceof JMenuBar)) {
            ((JMenuBar) c).setBorder(HiFiBorders.getMenuBarBorder());
        }
    }

    public void paint(Graphics g, JComponent c) {
        HiFiUtils.fillComponent(g, c);
    }
}