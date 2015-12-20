/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity;
/*
 *   TileEntityArmory
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Common.TileEntity.State.*;
import com.SmithsModding.SmithsCore.Common.TileEntity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public abstract class TileEntityArmory extends TileEntitySmithsCore implements IWorldNameable {
    private String name = "";
    private EnumFacing direction = EnumFacing.NORTH;

    /**
     * Constructor to create a new TileEntity for a SmithsCore Mod.
     * <p/>
     * Handles the setting of the core system values like the state, and the GUIManager.
     *
     * @param initialState The TE State that gets set on default when a new Instance is created.
     * @param manager      The GUIManager that handles interactins with events comming from UI's
     */
    protected TileEntityArmory (ITileEntityState initialState, IGUIManager manager) {
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
    public void writeToNBT (NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte(References.NBTTagCompoundData.TE.Basic.DIRECTION, (byte) direction.ordinal());

        if (this.hasCustomName()) {
            compound.setString(References.NBTTagCompoundData.TE.Basic.NAME, name);
        }
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
    public ChatComponentText getDisplayName () {
        return new ChatComponentText(name);
    }

    public void setDisplayName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

}
