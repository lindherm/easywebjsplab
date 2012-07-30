/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.bernstein;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;


/**
 * @author Michael Hagen
 */
public class BernsteinMenuBarUI extends BasicMenuBarUI {

    public static ComponentUI createUI(JComponent x) {
        return new BernsteinMenuBarUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        if ((c != null) && (c instanceof JMenuBar)) {
            ((JMenuBar) c).setBorder(BernsteinBorders.getMenuBarBorder());
            ((JMenuBar) c).setBorderPainted(true);
        }
    }

    public void paint(Graphics g, JComponent c) {
        BernsteinUtils.fillComponent(g, c);
    }
}