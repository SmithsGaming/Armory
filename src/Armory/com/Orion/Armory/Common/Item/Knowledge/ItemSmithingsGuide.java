/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Item.Knowledge;

import com.Orion.Armory.API.Item.InventoryItem;
import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.Common.Inventory.Items.InventorySmithingsGuideBlueprints;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemSmithingsGuide extends InventoryItem implements IBluePrintContainerItem {

    public ItemSmithingsGuide() {
        this.setMaxStackSize(1);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemSmithingsGuide);
    }

    @Override
    public void onUpdate(ItemStack pStack, World pWorld, Entity pEntity, int pSlotIndex, boolean pSelected) {
        if (pWorld.isRemote)
            return;

        if (pStack.getTagCompound() != null) {
            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.TicksSinceOpen)) {
                if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) < 6)
                    pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + 1);
            }

            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.TicksSinceClose)) {
                if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) > 0)
                    pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - 1);
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack pStack, World pWorld, EntityPlayer pPlayer) {
        if (pPlayer.isSneaking()) {
            return super.onItemRightClick(pStack, pWorld, pPlayer);
        }

        if (pStack.getTagCompound() == null)
            pStack.setTagCompound(new NBTTagCompound());

        if (!(pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState))) {
            pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, true);
            pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);

            return pStack;
        } else {
            if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceOpen);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 5);
            } else {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceClose);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, true);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);
            }
        }

        return pStack;
    }

    @Override
    public Entity createEntity(World pWorld, Entity pThrowingEntity, ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            pStack.setTagCompound(new NBTTagCompound());

        if (!(pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState))) {
            pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
            pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 0);

            return super.createEntity(pWorld, pThrowingEntity, pStack);
        } else {
            if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceOpen);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 0);
            } else {
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);
            }
        }
        return super.createEntity(pWorld, pThrowingEntity, pStack);
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
