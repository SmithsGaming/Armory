/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Item;

import com.Orion.Armory.Armory;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class InventoryItem extends Item {

    public UUID getItemStackID(ItemStack pStack) {
        if (!(pStack.getItem() instanceof InventoryItem)) {
            return null;
        }

        NBTTagCompound tStackCompound = pStack.getTagCompound();

        if (!(tStackCompound.hasKey(References.NBTTagCompoundData.Item.ItemInventory.ID))) {
            UUID tNewID = UUID.randomUUID();
            tStackCompound.setString(References.NBTTagCompoundData.Item.ItemInventory.ID, tNewID.toString());

            pStack.setTagCompound(tStackCompound);

            return tNewID;
        }

        return UUID.fromString(tStackCompound.getString(References.NBTTagCompoundData.Item.ItemInventory.ID));
    }

    public void assignItemStackNewID(ItemStack pStack) {
        if (!(pStack.getItem() instanceof InventoryItem)) {
            return;
        }

        NBTTagCompound tStackCompound = pStack.getTagCompound();

        UUID tNewID = UUID.randomUUID();
        tStackCompound.setString(References.NBTTagCompoundData.Item.ItemInventory.ID, tNewID.toString());

        pStack.setTagCompound(tStackCompound);
    }

    public void markStackOpenState(ItemStack pStack, boolean pState) {
        NBTTagCompound tStackCompound = pStack.getTagCompound();
        tStackCompound.setBoolean(References.NBTTagCompoundData.Item.ItemInventory.OPEN, pState);
        pStack.setTagCompound(tStackCompound);
    }

    public boolean getOpenState(ItemStack pStack) {
        if (!pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.ItemInventory.OPEN))
            return false;

        return pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Item.ItemInventory.OPEN);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack pStack, World pWorld, EntityPlayer pPlayer) {
        if (!pWorld.isRemote) {
            assignItemStackNewID(pStack);
            markStackOpenState(pStack, true);
            pPlayer.openGui(Armory.instance, getInventoryGuiIndex(pPlayer), pWorld, (int) pPlayer.posX, (int) pPlayer.posY, (int) pPlayer.posZ);
        }

        return pStack;
    }

    public abstract Integer getInventoryGuiIndex(EntityPlayer pPlayer);

    public abstract IInventory getInventoryFromItemStack(ItemStack pStack);
}
