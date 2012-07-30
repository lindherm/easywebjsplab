/*
 * Copyright 2012 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.plaf.ComponentUI;

/**
 * @author  Michael Hagen
 */
public class TextureRootPaneUI extends BaseRootPaneUI
{
   public static ComponentUI createUI(JComponent c) { 
       return new TextureRootPaneUI();
   }
   
   public BaseTitlePane createTitlePane(JRootPane root) { 
       return new TextureTitlePane(root, this);
   }   
}
