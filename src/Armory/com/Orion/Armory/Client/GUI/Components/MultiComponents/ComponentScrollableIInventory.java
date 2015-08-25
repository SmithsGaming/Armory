/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;

public class ComponentScrollableIInventory extends AbstractGUIMultiComponent {

    public ComponentScrollableIInventory(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, int pStartStackIndex, int pHorizontalStackCount, int pVerticalStackCount) {
        super(pGui, pInternalName, pLeft, pTop, 0, 0);
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return iSubComponents.handleMouseClicked(pMouseX - iLeft, pMouseY - iTop, pMouseButton);
    }
}
