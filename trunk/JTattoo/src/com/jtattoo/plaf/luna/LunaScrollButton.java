/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.LazyImageIcon;
import com.jtattoo.plaf.XPScrollButton;
import javax.swing.Icon;

/**
 * @author  Michael Hagen
 */
public class LunaScrollButton extends XPScrollButton {

    protected static Icon upArrowIcon = null;
    protected static Icon downArrowIcon = null;
    protected static Icon leftArrowIcon = null;
    protected static Icon rightArrowIcon = null;

    public LunaScrollButton(int direction, int width) {
        super(direction, width);
    }

    public Icon getUpArrowIcon() {
        if (upArrowIcon == null) {
            upArrowIcon = new LazyImageIcon("luna/icons/UpArrow.gif");
        }
        return upArrowIcon;
    }

    public Icon getDownArrowIcon() {
        if (downArrowIcon == null) {
            downArrowIcon = new LazyImageIcon("luna/icons/DownArrow.gif");
        }
        return downArrowIcon;
    }

    public Icon getLeftArrowIcon() {
        if (leftArrowIcon == null) {
            leftArrowIcon = new LazyImageIcon("luna/icons/LeftArrow.gif");
        }
        return leftArrowIcon;
    }

    public Icon getRightArrowIcon() {
        if (rightArrowIcon == null) {
            rightArrowIcon = new LazyImageIcon("luna/icons/RightArrow.gif");
        }
        return rightArrowIcon;
    }

}
