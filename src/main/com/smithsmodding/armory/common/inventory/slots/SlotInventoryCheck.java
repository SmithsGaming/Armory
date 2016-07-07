package com.smithsmodding.armory.common.inventory.slots;

import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.item.ItemStack;

/**
 * Author Marc (Created on: 30.03.2016)
 */
public class SlotInventoryCheck extends SlotSmithsCore {
    private final int stackLimit;

    public SlotInventoryCheck(IItemStorage pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);

        this.stackLimit = 1;
    }

    public SlotInventoryCheck(IItemStorage pInventory, int pSlotIndex, int pXLocation, int pYLocation, int stackLimit) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);

        this.stackLimit = stackLimit;
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack) {
        return inventory.isItemValidForSlot(getSlotIndex(), pItemStack);
    }

    @Override
    public int getSlotStackLimit() {
        return stackLimit;
    }
}
