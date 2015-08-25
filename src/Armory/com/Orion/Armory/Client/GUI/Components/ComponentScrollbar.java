/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;

public class ComponentScrollbar extends AbstractGUIMultiComponent {

    public ComponentScrollbar(IComponentHost pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
