package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Marc on 27.02.2016.
 */
public class FireplaceState implements ITileEntityState {

    private float totalBurningTicksLeft = 0F;
    private float totalBurningTicks = 0F;

    private float maxTemperature = 1250;
    private float currentTemperature = 20;
    private float lastTemperature = 20;
    private float lastAddedHeat = 0;

    private float cookingProgress = 0f;
    private float cookingSpeedMultiplier = 1f;

    public Object getData(Object requestingComponent, String propertyRequested) {
        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            return totalBurningTicksLeft;

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            return totalBurningTicks;

        return 0F;
    }

    public void setData(Object sendingComponent, String propertySend, Object data) {
        if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            totalBurningTicksLeft = (Float) data;
        else if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            totalBurningTicks = (Float) data;
        else
            return;
    }


    /**
     * Method called when this state get attached to a TE. Allows it to store a reference or modify values of the TE.
     *
     * @param tileEntitySmithsCore The TE this state got attached to.
     */
    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {

    }

    /**
     * Called to indicate this TE that some of its values may have been updated. Use it to perform additional
     * calculation on this data.
     */
    @Override
    public void onStateUpdated() {

    }

    /**
     * Method called by the Attached TE to indicate that it is being detached and discarded by its TE. Allows you to
     * handle the disconnect from the state gracefully.
     */
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

        totalBurningTicksLeft = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.FUELSTACKBURNINGTIME);
        totalBurningTicks = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.FUELSTACKFUELAMOUNT);

        currentTemperature = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.CURRENTTEMPERATURE);
        maxTemperature = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.MAXTEMPERATURE);
        lastAddedHeat = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.LASTADDEDHEAT);
        lastTemperature = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.LASTTEMPERATURE);

        cookingProgress = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS);
        cookingSpeedMultiplier = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED);
    }

    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.FUELSTACKBURNINGTIME, totalBurningTicksLeft);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.FUELSTACKFUELAMOUNT, totalBurningTicks);

        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.CURRENTTEMPERATURE, currentTemperature);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.MAXTEMPERATURE, maxTemperature);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.LASTADDEDHEAT, lastAddedHeat);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.LASTTEMPERATURE, lastTemperature);

        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS, cookingProgress);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED, cookingSpeedMultiplier);

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


    public float getLastAddedHeat() {
        return lastAddedHeat;
    }

    public void setLastAddedHeat(float lastAddedHeat) {
        this.lastAddedHeat = lastAddedHeat;
    }

    public float getLastTemperature() {
        return lastTemperature;
    }

    public void setLastTemperature(float lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
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
}
