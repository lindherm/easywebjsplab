/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.XPScrollBarUI;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author  Michael Hagen
 */
public class MintScrollBarUI extends XPScrollBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new MintScrollBarUI();
    }

    protected JButton createDecreaseButton(int orientation) {
        return new MintScrollButton(orientation, scrollBarWidth);
    }

    protected JButton createIncreaseButton(int orientation) {
        return new MintScrollButton(orientation, scrollBarWidth);
    }

}
