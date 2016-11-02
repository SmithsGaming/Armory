package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public class TileEntityPumpState implements ITileEntityState {
    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {

    }

    @Override
    public void onStateUpdated() {

    }

    @Override
    public void onStateDestroyed() {

    }

    @Override
    public boolean requiresNBTStorage() {
        return false;
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {

    }

    @Override
    public NBTBase writeToNBTTagCompound() {
        return new NBTTagCompound();
    }

    @Override
    public boolean requiresSynchronization() {
        return false;
    }

    @Override
    public void readFromSynchronizationCompound(NBTBase stateData) {

    }

    @Override
    public NBTBase writeToSynchronizationCompound() {
        return new NBTTagCompound();
    }
}
