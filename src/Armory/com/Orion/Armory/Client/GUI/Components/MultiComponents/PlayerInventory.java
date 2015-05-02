package com.Orion.Armory.Client.GUI.Components.MultiComponents;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIMultiComponent;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * Created by Orion
 * Created on 01.05.2015
 * 15:39
 * <p/>
 * Copyrighted according to Project specific license
 */
public class PlayerInventory extends AbstractGUIMultiComponent
{

    public PlayerInventory(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, int pPlayerInventoryStartSlotIndex, ComponentBorder.CornerTypes pConnectionType) {
        super(pGui, pInternalName, pLeft, pTop, 176, 90);

        getComponents().add(new ComponentBorder(pGui, pInternalName + ".Background", pLeft, pTop, 175, 90, Colors.DEFAULT, pConnectionType));

        for (int tSlotIndex = pPlayerInventoryStartSlotIndex; tSlotIndex < pGui.inventorySlots.inventorySlots.size(); tSlotIndex ++)
        {
            int tRowIndex = ((tSlotIndex - pPlayerInventoryStartSlotIndex) / 9);
            int tColumnIndex = (tSlotIndex - pPlayerInventoryStartSlotIndex) % 9;

            if (tRowIndex < 3)
            {
                addComponent(new ComponentSlot(pGui, pInternalName + ".Slot." + tSlotIndex, 18, 18, 7 + tColumnIndex * 18, 7 + tRowIndex * 18, Textures.Gui.Basic.Slots.DEFAULT));
            }
            else {
                addComponent(new ComponentSlot(pGui, pInternalName + ".Slot." + tSlotIndex, 18, 18, 7 + tColumnIndex * 18, 11 + tRowIndex * 18, Textures.Gui.Basic.Slots.DEFAULT));
            }
        }
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
