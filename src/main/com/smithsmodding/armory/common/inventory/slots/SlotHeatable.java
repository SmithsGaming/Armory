package com.smithsmodding.armory.common.inventory.slots;
/*
 *   SlotHeatable
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotHeatable extends SlotSmithsCore {

    private int meltingProgressIndex;

    public SlotHeatable(IItemStorage pInventory, int pSlotIndex, int pXLocation, int pYLocation, int meltingProgressIndex) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);

        this.meltingProgressIndex = meltingProgressIndex;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            return true;
        }
        return HeatableItemRegistry.getInstance().isHeatable(pItemStack);
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        if (!(((IItemStorage.IInventoryWrapper) inventory).getStorage() instanceof TileEntityForge))
            return true;

        TileEntityForge forge = ((TileEntityForge) ((IItemStorage.IInventoryWrapper) inventory).getStorage());

        return forge.getState().getMeltingProgess(meltingProgressIndex) == 0f;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public void onSlotChanged() {
        return;
    }
}
