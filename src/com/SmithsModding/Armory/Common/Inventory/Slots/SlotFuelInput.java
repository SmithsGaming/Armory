package com.SmithsModding.Armory.Common.Inventory.Slots;
/*
 *   SlotFuelInput
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;

public class SlotFuelInput extends Slot {

    public SlotFuelInput (IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid (ItemStack pItemStack) {
        return TileEntityFurnace.isItemFuel(pItemStack);
    }
}
