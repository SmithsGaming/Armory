package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.common.tileentity.guimanagers.*;
import com.smithsmodding.armory.common.tileentity.state.*;
import com.smithsmodding.armory.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvil extends TileEntityArmory implements IInventory {

    public TileEntityBlackSmithsAnvil () {
        super(new BlackSmithsAnvilState(), null);

        this.setManager(new BlackSmithsAnvilGuiManager(this));
    }

    /**
     * Getter for the Containers ID. Used to identify the container over the network. If this relates to TileEntities,
     * it should contain a ID and a location based ID so that multiple instances of this container matched up to
     * different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    @Override
    public String getContainerID () {
        return References.InternalNames.TileEntities.ArmorsAnvil + " - " + getLocation().toString();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory () {
        return 0;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Override
    public ItemStack getStackInSlot (int index) {
        return null;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
    @Override
    public ItemStack decrStackSize (int index, int count) {
        return null;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index
     */
    @Override
    public ItemStack removeStackFromSlot (int index) {
        return null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents (int index, ItemStack stack) {

    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit () {
        return 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param player
     */
    @Override
    public boolean isUseableByPlayer (EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory (EntityPlayer player) {

    }

    @Override
    public void closeInventory (EntityPlayer player) {

    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param index
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot (int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField (int id) {
        return 0;
    }

    @Override
    public void setField (int id, int value) {

    }

    @Override
    public int getFieldCount () {
        return 0;
    }

    @Override
    public void clear () {

    }
}
