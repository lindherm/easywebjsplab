/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.BaseInternalFrameUI;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class TextureInternalFrameUI extends BaseInternalFrameUI {

    public TextureInternalFrameUI(JInternalFrame b) {
        super(b); 
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new TextureInternalFrameUI((JInternalFrame)c);
    }
    
    protected JComponent createNorthPane(JInternalFrame w)  {
        titlePane = new TextureInternalFrameTitlePane(w);
        return titlePane;
    }
    
}

