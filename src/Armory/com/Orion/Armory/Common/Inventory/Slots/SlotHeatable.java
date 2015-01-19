package com.Orion.Armory.Common.Inventory.Slots;
/*
 *   SlotHeatable
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHeatable extends Slot
{

    public SlotHeatable(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack)
    {
        if (pItemStack.getItem() instanceof ItemHeatedIngot) { return true; }
        return HeatedIngotFactory.getInstance().isHeatable(pItemStack);
    }
}
