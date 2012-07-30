/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 * @author Michael Hagen
 */
public class BasePopupMenuUI extends BasicPopupMenuUI {

    protected static Robot robot = null;
    protected BufferedImage screenImage = null;
    protected MyPopupMenuListener myPopupListener = null;

    public static ComponentUI createUI(JComponent c) {
        return new BasePopupMenuUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.setOpaque(true);
    }

    public void installListeners() {
        super.installListeners();
        if (!isMenuOpaque()) {
            myPopupListener = new MyPopupMenuListener(this);
            popupMenu.addPopupMenuListener(myPopupListener);
        }
    }

    public void uninstallListeners() {
        if (!isMenuOpaque()) {
            popupMenu.removePopupMenuListener(myPopupListener);
        }
        super.uninstallListeners();
    }

    private boolean isMenuOpaque() {
        return (AbstractLookAndFeel.getTheme().isMenuOpaque() || (getRobot() == null));
    }

    private Robot getRobot() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (Exception ex) {
            }
        }
        return robot;
    }

    public Popup getPopup(JPopupMenu popupMenu, int x, int y) {
        if (!isMenuOpaque()) {
            try {
                Dimension size = popupMenu.getPreferredSize();
                Rectangle screenRect = new Rectangle(x, y, size.width, size.height);
                screenImage = getRobot().createScreenCapture(screenRect);
            } catch (Exception ex) {
                screenImage = null;
            }
        }
        return super.getPopup(popupMenu, x, y);
    }

    private void resetScreenImage() {
        screenImage = null;
    }

    public void update(Graphics g, JComponent c) {
        if (screenImage != null) {
            g.drawImage(screenImage, 0, 0, null);
        } else {
            g.setColor(Color.white);
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }

//----------------------------------------------------------------------------------------    
// inner classes    
//----------------------------------------------------------------------------------------    
    public static class MyPopupMenuListener implements PopupMenuListener {

        private BasePopupMenuUI popupMenuUI = null;

        public MyPopupMenuListener(BasePopupMenuUI aPopupMenuUI) {
            popupMenuUI = aPopupMenuUI;
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (popupMenuUI.screenImage != null) {
                JPopupMenu popup = (JPopupMenu) e.getSource();
                JRootPane root = popup.getRootPane();
                if (popup.isShowing() && root.isShowing()) {
                    Point ptPopup = popup.getLocationOnScreen();
                    Point ptRoot = root.getLocationOnScreen();
                    Graphics g = popup.getRootPane().getGraphics();
                    g.drawImage(popupMenuUI.screenImage, ptPopup.x - ptRoot.x, ptPopup.y - ptRoot.y, null);
                    popupMenuUI.resetScreenImage();
                }
            }
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }
    }
}

