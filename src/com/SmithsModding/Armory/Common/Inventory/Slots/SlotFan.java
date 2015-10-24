package com.SmithsModding.Armory.Common.Inventory.Slots;

import com.SmithsModding.Armory.Common.Item.ItemFan;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 23.04.2015
 * 13:04
 * <p/>
 * Copyrighted according to Project specific license
 */
public class SlotFan extends Slot {
    public SlotFan(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack) {
        return (pItemStack.getItem() instanceof ItemFan);
    }
}
