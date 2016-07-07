package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityFireplaceState extends TileEntityForgeBaseState<TileEntityFireplace> implements IForgeFuelDataContainer {

    boolean isBurning;

    int totalBurningTicksOnCurrentFuel;
    int burningTicksLeftOnCurrentFuel;

    private float cookingProgress = 0f;
    private float cookingSpeedMultiplier = 1f;

    public TileEntityFireplaceState() {
        this.currentTemp = 20f;
        this.lastChange = 0f;
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {
        super.readFromNBTTagCompound(stateData);

        NBTTagCompound compound = (NBTTagCompound) stateData;

        cookingProgress = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS);
        cookingSpeedMultiplier = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED);
    }

    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = (NBTTagCompound) super.writeToNBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS, cookingProgress);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED, cookingSpeedMultiplier);

        return compound;
    }

    public float getCookingProgress() {
        return cookingProgress;
    }

    public void setCookingProgress(float cookingProgress) {
        this.cookingProgress = cookingProgress;
    }

    public float getCookingSpeedMultiplier() {
        return cookingSpeedMultiplier;
    }

    public void setCookingSpeedMultiplier(float cookingSpeedMultiplier) {
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
    }

    @Override
    public boolean isBurning() {
        return isBurning;
    }

    @Override
    public void setBurning(boolean burning) {
        this.isBurning = burning;
    }

    @Override
    public int getTotalBurningTicksOnCurrentFuel() {
        return totalBurningTicksOnCurrentFuel;
    }

    @Override
    public void setTotalBurningTicksOnCurrentFuel(int totalBurningTicksOnCurrentFuel) {
        this.totalBurningTicksOnCurrentFuel = totalBurningTicksOnCurrentFuel;
    }

    @Override
    public void changeTotalBurningTicksOnCurrentFuel(int change) {
        totalBurningTicksOnCurrentFuel += change;
    }

    @Override
    public int getBurningTicksLeftOnCurrentFuel() {
        return burningTicksLeftOnCurrentFuel;
    }

    @Override
    public void setBurningTicksLeftOnCurrentFuel(int burningTicksLeftOnCurrentFuel) {
        this.burningTicksLeftOnCurrentFuel = burningTicksLeftOnCurrentFuel;
    }

    @Override
    public void changeBurningTicksLeftOnCurrentFuel(int change) {
        burningTicksLeftOnCurrentFuel += change;
    }

    @Override
    public void resetBurningTicksLeftOnCurrentFuel() {
        setBurningTicksLeftOnCurrentFuel(getTotalBurningTicksOnCurrentFuel());
    }
}
