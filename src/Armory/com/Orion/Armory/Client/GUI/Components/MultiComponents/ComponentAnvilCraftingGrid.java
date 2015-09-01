/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentProgressBar;
import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.Textures;

public class ComponentAnvilCraftingGrid extends AbstractGUIMultiComponent {

    public ComponentAnvilCraftingGrid(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pStartPostitionCraftingSlots, int pEndPostitionCraftingSlots, Color pBackground, Color pForeground) {
        super(pHost, pInternalName, pLeft, pTop, 104, 104);

        getComponents().add(new ComponentBorder(pHost, pInternalName + ".Background", 0, 0, 162, 104, pBackground, ComponentBorder.CornerTypes.Inwarts));

        for (int tSlotIndex = pStartPostitionCraftingSlots; tSlotIndex < pEndPostitionCraftingSlots; tSlotIndex++) {
            int tRowIndex = ((tSlotIndex - pStartPostitionCraftingSlots) / 5);
            int tColumnIndex = (tSlotIndex - pStartPostitionCraftingSlots) % 5;

            CustomResource tIconResource = null;
            if (tRowIndex == 2) {
                if (tColumnIndex == 1)
                    tIconResource = Textures.Gui.Anvil.UPGRADETOOLSLOT;

                if (tColumnIndex == 3)
                    tIconResource = Textures.Gui.Anvil.UPGRADEPAYMENTSLOT;
            }
            addComponent(new ComponentSlot(pHost, pInternalName + ".Slot.Crafting." + tSlotIndex, tSlotIndex, 18, 18, 7 + tColumnIndex * 18, 7 + tRowIndex * 18, Textures.Gui.Basic.Slots.DEFAULT, pBackground, tIconResource));
        }

        addComponent(new ComponentProgressBar(pHost, pInternalName + ".Progress.Arrow.1", 105, 45, pForeground, pBackground));
        addComponent(new ComponentSlot(pHost, pInternalName + ".Slot.Output", pEndPostitionCraftingSlots, 18, 18, 137, 43, Textures.Gui.Basic.Slots.DEFAULT, pBackground));
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
