package com.Orion.Armory.Common.Inventory.Slots;
/*
 *   SlotFuelInput
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFuelInput extends Slot
{

    public SlotFuelInput(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack)
    {
        return TileEntityFurnace.isItemFuel(pItemStack);
    }
}
