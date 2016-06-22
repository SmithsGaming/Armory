/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.tileentity;
/*
 *   TileEntityArmory
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.smithsmodding.armory.api.References;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IWorldNameable;

public abstract class TileEntityArmory<S extends ITileEntityState, G extends IGUIManager> extends TileEntitySmithsCore<S, G> implements IWorldNameable {
    private String name = "";
    private EnumFacing direction = EnumFacing.NORTH;

    /**
     * Constructor to create a new tileentity for a smithscore Mod.
     * <p/>
     * Handles the setting of the core system values like the state, and the GUIManager.
     *
     * @param initialState The TE state that gets set on default when a new Instance is created.
     * @param manager      The GUIManager that handles interactins with events comming from UI's
     */
    protected TileEntityArmory(S initialState, G manager) {
        super(initialState, manager);
    }


    @Override
    public void readFromNBT (NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey(References.NBTTagCompoundData.TE.Basic.DIRECTION)) {
            this.direction = EnumFacing.getHorizontal(compound.getByte(References.NBTTagCompoundData.TE.Basic.DIRECTION));
        }

        if (compound.hasKey(References.NBTTagCompoundData.TE.Basic.NAME)) {
            this.name = compound.getString(References.NBTTagCompoundData.TE.Basic.NAME);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setByte(References.NBTTagCompoundData.TE.Basic.DIRECTION, (byte) direction.ordinal());

        if (this.hasCustomName()) {
            compound.setString(References.NBTTagCompoundData.TE.Basic.NAME, name);
        }

        return compound;
    }


    public void setDirection (int newDirection) {
        direction = EnumFacing.getHorizontal(newDirection);
    }

    public EnumFacing getDirection () {
        return this.direction;
    }

    public void setDirection (EnumFacing newDirection) {
        this.direction = newDirection;
    }


    public boolean hasCustomName () {
        return name != null && name.length() > 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(name);
    }

    public void setDisplayName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

}
