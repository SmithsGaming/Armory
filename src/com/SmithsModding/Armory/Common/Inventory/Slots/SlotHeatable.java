package com.SmithsModding.Armory.Common.Inventory.Slots;
/*
 *   SlotHeatable
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Item.ItemHeatedItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHeatable extends Slot {

    public SlotHeatable(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            return true;
        }
        return HeatedItemFactory.getInstance().isHeatable(pItemStack);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
