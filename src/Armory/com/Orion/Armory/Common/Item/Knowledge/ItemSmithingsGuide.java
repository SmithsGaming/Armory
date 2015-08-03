/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Item.Knowledge;

import com.Orion.Armory.API.Item.InventoryItem;
import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.Common.Inventory.Items.InventorySmithingsGuideBlueprints;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemSmithingsGuide extends InventoryItem implements IBluePrintContainerItem {

    @Override
    public ItemStack onItemRightClick(ItemStack pStack, World pWorld, EntityPlayer pPlayer) {
        if (pPlayer.isSneaking()) {
            return super.onItemRightClick(pStack, pWorld, pPlayer);
        }

        return pStack;
    }

    @Override
    public Integer getInventoryGuiIndex(EntityPlayer pPlayer) {
        if (pPlayer.isSneaking())
            return References.GuiIDs.SMITHINGSGUIDEINVENTORY;

        return References.GuiIDs.SMITHINGSGUIDE;
    }

    @Override
    public IInventory getInventoryFromItemStack(ItemStack pStack) {
        return new InventorySmithingsGuideBlueprints(pStack);
    }

    @Override
    public ArrayList<ItemStack> getStoredBluePrints(ItemStack pStack) {
        InventorySmithingsGuideBlueprints tBlueprintInventory = (InventorySmithingsGuideBlueprints) getInventoryFromItemStack(pStack);

        return tBlueprintInventory.getBluePrints();
    }
}
