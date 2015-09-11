/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Inventory.Slots;

import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotReportable extends Slot {
    ContainerArmory iContainer;
    ItemStack iCurrentStack;

    public SlotReportable(ContainerArmory pContainer, IInventory pInventory, int pSlotIndex, int pX, int pY) {
        super(pInventory, pSlotIndex, pX, pY);

        iContainer = pContainer;
    }

    @Override
    public void onSlotChanged() {
        if (!ItemStackHelper.equals(iCurrentStack, this.getStack())) {
            iContainer.OnContainerChanged(References.InternalNames.InputHandlers.Components.SLOTCHANGED);
            iCurrentStack = this.getStack();
        }

        super.onSlotChanged();
    }
}
