package com.smithsmodding.armory.common.inventory.slots;
/*
 *   SlotFuelInput
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.smithsmodding.smithscore.common.slots.*;
import com.smithsmodding.smithscore.common.tileentity.Capabilities.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;

public class SlotFuelInput extends SlotSmithsCore {

    public SlotFuelInput (ISmithsCoreItemHandler pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid (ItemStack pItemStack) {
        return TileEntityFurnace.isItemFuel(pItemStack);
    }
}
