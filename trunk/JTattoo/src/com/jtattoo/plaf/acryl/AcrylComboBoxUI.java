/*
 * Copyright 2005 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */
package com.jtattoo.plaf.acryl;

import com.jtattoo.plaf.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class AcrylComboBoxUI extends BaseComboBoxUI {

    public static ComponentUI createUI(JComponent c) {
        return new AcrylComboBoxUI();
    }

    public JButton createArrowButton() {
        ArrowButton button = new BaseComboBoxUI.ArrowButton();
        Color borderColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
        if (JTattooUtilities.isLeftToRight(comboBox)) {
            Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, borderColor);
            button.setBorder(border);
        } else {
            Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor);
            button.setBorder(border);
        }
        return button;
    }

    protected void setButtonBorder() {
        Color borderColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
        if (JTattooUtilities.isLeftToRight(comboBox)) {
            Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, borderColor);
            arrowButton.setBorder(border);
        } else {
            Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor);
            arrowButton.setBorder(border);
        }
    }
}