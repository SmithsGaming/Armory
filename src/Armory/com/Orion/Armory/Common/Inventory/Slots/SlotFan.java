package com.Orion.Armory.Common.Inventory.Slots;

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Item.ItemFan;
import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import net.minecraft.client.renderer.texture.Stitcher;
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
public class SlotFan extends Slot
{
    public SlotFan(IInventory pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack)
    {
        return (pItemStack.getItem() instanceof ItemFan);
    }
}
