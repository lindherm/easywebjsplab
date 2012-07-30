/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.XPScrollBarUI;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author  Michael Hagen
 */
public class TextureScrollBarUI extends XPScrollBarUI {

//    private static Color rolloverColors[] = null;
//    private static Color dragColors[] = null;

    public static ComponentUI createUI(JComponent c) {
        return new TextureScrollBarUI();
    }

//    protected void installDefaults() {
//        super.installDefaults();
//        Color colors[] = AbstractLookAndFeel.getTheme().getThumbColors();
//        rolloverColors = new Color[colors.length];
//        dragColors = new Color[colors.length];
//        for (int i = 0; i < colors.length; i++) {
//            rolloverColors[i] = ColorHelper.darker(colors[i], 6);
//            dragColors[i] = ColorHelper.darker(colors[i], 12);
//        }
//    }

    protected JButton createDecreaseButton(int orientation) {
        return new TextureScrollButton(orientation, scrollBarWidth);
    }

    protected JButton createIncreaseButton(int orientation) {
        return new TextureScrollButton(orientation, scrollBarWidth);
    }

}
