/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Inventory.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotScrollableInventory extends Slot {

    private int iScrolledSlotIndex;

    public SlotScrollableInventory(IInventory pInventory, int pSlotIndex, int pX, int pY) {
        super(pInventory, pSlotIndex, pX, pY);

        iScrolledSlotIndex = super.getSlotIndex();
    }

    @Override
    public ItemStack decrStackSize(int pAmount) {
        return this.inventory.decrStackSize(this.iScrolledSlotIndex, pAmount);
    }

    @Override
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.iScrolledSlotIndex);
    }

    @Override
    public int getSlotIndex() {
        return iScrolledSlotIndex;
    }

    public void setSlotIndex(int pNewSlotIndex) {
        iScrolledSlotIndex = pNewSlotIndex;
    }

    @Override
    public boolean isSlotInInventory(IInventory pInventory, int pSlotIndex) {
        return pInventory == this.inventory && pSlotIndex == this.iScrolledSlotIndex;
    }

    @Override
    public void putStack(ItemStack pStack) {
        this.inventory.setInventorySlotContents(this.iScrolledSlotIndex, pStack);
        this.onSlotChanged();
    }
}
