package com.smithsmodding.armory.common.inventory.slots;
/*
 *   SlotFuelInput
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFuelInput extends SlotSmithsCore {

    public SlotFuelInput(IItemStorage pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack) {
        return TileEntityFurnace.isItemFuel(pItemStack);
    }
}
