package com.smithsmodding.armory.common.inventory.slots;
/*
 *   SlotHeatable
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.item.ItemStack;

public class SlotHeatable extends SlotSmithsCore {

    public SlotHeatable(IItemStorage pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid (ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            return true;
        }
        return HeatableItemRegistry.getInstance().isHeatable(pItemStack);
    }

    @Override
    public int getSlotStackLimit () {
        return 1;
    }
}
