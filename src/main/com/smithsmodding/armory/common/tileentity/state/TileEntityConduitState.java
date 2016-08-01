package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import javax.vecmath.Vector3f;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class TileEntityConduitState implements ITileEntityState {

    Vector3f flowVector = new Vector3f();
    
    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {

    }

    @Override
    public void onStateUpdated() {
        if (flowVector.x > 0)
            flowVector.x -= 1;
        if (flowVector.x < 0)
            flowVector.x += 1;
        if (flowVector.y > 0)
            flowVector.y -= 1;
        if (flowVector.y < 0)
            flowVector.y += 1;
        if (flowVector.z > 0)
            flowVector.z -= 1;
        if (flowVector.z < 0)
            flowVector.z += 1;
    }

    @Override
    public void onStateDestroyed() {

    }

    @Override
    public boolean requiresNBTStorage() {
        return true;
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {
        NBTTagCompound compound = (NBTTagCompound) stateData;

        flowVector.x = compound.getFloat(References.NBTTagCompoundData.TE.Conduit.FLOWX);
        flowVector.y = compound.getFloat(References.NBTTagCompoundData.TE.Conduit.FLOWY);
        flowVector.z = compound.getFloat(References.NBTTagCompoundData.TE.Conduit.FLOWZ);
    }

    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.Conduit.FLOWX, flowVector.x);
        compound.setFloat(References.NBTTagCompoundData.TE.Conduit.FLOWY, flowVector.y);
        compound.setFloat(References.NBTTagCompoundData.TE.Conduit.FLOWZ, flowVector.z);

        return compound;
    }

    @Override
    public boolean requiresSynchronization() {
        return true;
    }

    @Override
    public void readFromSynchronizationCompound(NBTBase stateData) {
        readFromNBTTagCompound(stateData);
    }

    @Override
    public NBTBase writeToSynchronizationCompound() {
        return writeToNBTTagCompound();
    }

    public boolean canExtractFromDirection(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                return flowVector.x >= 0;
            case SOUTH:
                return flowVector.x <= 0;
            case EAST:
                return flowVector.z >= 0;
            case WEST:
                return flowVector.z <= 0;
            case UP:
                return flowVector.y >= 0;
            case DOWN:
                return flowVector.y <= 0;
            default:
                return false;
        }
    }

    public boolean canInsertFromDirection(EnumFacing facing) {
        return canExtractFromDirection(facing.getOpposite());
    }

    public void onExtraction(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.x < 20)
                    flowVector.x = 20;
                break;
            case SOUTH:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.x > -20)
                    flowVector.x = -20;
                break;
            case EAST:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.z < 20)
                    flowVector.z = 20;
                break;
            case WEST:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.z > -20)
                    flowVector.z = -20;
                break;
            case UP:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.y < 20)
                    flowVector.y = 20;
                break;
            case DOWN:
                if (!canExtractFromDirection(facing)) return;
                if (flowVector.y > -20)
                    flowVector.y = -20;
                break;
            default:
                break;
        }
    }

    public void onInsertion(EnumFacing facing) {
        onExtraction(facing.getOpposite());
    }
}
