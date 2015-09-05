/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.Slots;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.References;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBlueprintContainer extends Slot {
    ContainerArmory iContainer;

    public SlotBlueprintContainer(ContainerArmory pContainer, IInventory pInventory, int pSlotIndex, int pX, int pY) {
        super(pInventory, pSlotIndex, pX, pY);

        iContainer = pContainer;
    }

    @Override
    public void onSlotChange(ItemStack pFirstStack, ItemStack pSecondStack) {
        if (pFirstStack == null && pSecondStack != null) {
            iContainer.OnContainerChanged(References.InternalNames.InputHandlers.Components.SLOTFILLED);
        }

        if (pFirstStack != null && pSecondStack == null) {
            iContainer.OnContainerChanged(References.InternalNames.InputHandlers.Components.SLOTEMPTIED);
        }

        if (pFirstStack != null && pSecondStack != null) {
            iContainer.OnContainerChanged(References.InternalNames.InputHandlers.Components.SLOTCHANGED);
        }

        super.onSlotChange(pFirstStack, pSecondStack);
    }

    @Override
    public boolean isItemValid(ItemStack pNewStack) {
        return pNewStack.getItem() instanceof IBluePrintContainerItem;
    }
}
