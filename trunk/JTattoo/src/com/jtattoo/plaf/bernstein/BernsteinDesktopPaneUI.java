package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.BaseDesktopPaneUI;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BernsteinDesktopPaneUI extends BaseDesktopPaneUI {

    private static BernsteinDesktopPaneUI desktopPaneUI = null;

    public static ComponentUI createUI(JComponent c) {
        if (desktopPaneUI == null) {
            desktopPaneUI = new BernsteinDesktopPaneUI();
        }
        return desktopPaneUI;
    }

    public void update(Graphics g, JComponent c) {
        if (c.getClientProperty("backgroundTexture") == null) {
            BernsteinUtils.fillComponent(g, c);
        } else {
            super.update(g, c);
        }
    }
}
