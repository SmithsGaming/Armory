package com.SmithsModding.Armory.Client.GUI.Components.MultiComponents;

import com.SmithsModding.Armory.Client.GUI.Components.ComponentBorder;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentProgressBar;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentSlot;
import com.SmithsModding.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Util.Client.Color.Color;
import com.SmithsModding.Armory.Util.Client.Textures;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 11:31
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentExtendedCraftingGrid extends AbstractGUIMultiComponent {

    public ComponentExtendedCraftingGrid(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pStartPostitionCraftingSlots, int pEndPostitionCraftingSlots, Color pBackground, Color pForeground) {
        super(pHost, pInternalName, pLeft, pTop, 104, 104);

        getComponents().add(new ComponentBorder(pHost, pInternalName + ".Background", 0, 0, 162, 104, pBackground, ComponentBorder.CornerTypes.Inwarts));

        for (int tSlotIndex = pStartPostitionCraftingSlots; tSlotIndex < pEndPostitionCraftingSlots; tSlotIndex++) {
            int tRowIndex = ((tSlotIndex - pStartPostitionCraftingSlots) / 5);
            int tColumnIndex = (tSlotIndex - pStartPostitionCraftingSlots) % 5;

            addComponent(new ComponentSlot(pHost, pInternalName + ".Slot.Crafting." + tSlotIndex, tSlotIndex, 18, 18, 7 + tColumnIndex * 18, 7 + tRowIndex * 18, Textures.Gui.Basic.Slots.DEFAULT, pBackground));
        }

        addComponent(new ComponentProgressBar(pHost, pInternalName + ".Progress.Arrow.1", 105, 45, pForeground, pBackground));
        addComponent(new ComponentSlot(pHost, pInternalName + ".Slot.Output", pEndPostitionCraftingSlots, 18, 18, 137, 43, Textures.Gui.Basic.Slots.DEFAULT, pBackground));
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
