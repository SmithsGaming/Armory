package com.smithsmodding.armory.common.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @Author Marc (Created on: 30.03.2016)
 */
public class SlotInventoryCheck extends Slot {
    private final int stackLimit;

    public SlotInventoryCheck(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);

        this.stackLimit = 1;
    }

    public SlotInventoryCheck(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation, int stackLimit) {
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
