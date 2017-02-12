package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForgeBase;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForgeBaseState<I extends TileEntityForgeBase> implements ITileEntityState {

    float currentTemp = 20;
    float lastTemp = 20;
    float lastChange = 0;
    float maxTemp;
    float lastNegativeTerm;
    float lastPositiveTerm;
    private I tileEntity;

    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {
        this.tileEntity = (I) tileEntitySmithsCore;
    }

    @Override
    public void onStateUpdated() {

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
        try {
            NBTTagCompound compound = (NBTTagCompound) stateData;

            currentTemp = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.CURRENTTEMPERATURE);
            maxTemp = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.MAXTEMPERATURE);
            lastChange = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTADDEDHEAT);
            lastTemp = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTTEMPERATURE);

            lastNegativeTerm = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTNEGATIVEINFLUENCE);
            lastPositiveTerm = compound.getFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTPOSITIVEINFLUENCE);
        } catch (Exception ex) {
            ModLogger.getInstance().error(new Exception("Failed to load NBT Data for Forge.", ex));

            currentTemp = 20f;
            maxTemp = 2750f;
            lastChange = 0f;
            lastTemp = 20f;

            lastNegativeTerm = 0f;
            lastPositiveTerm = 0f;
        }
    }

    @Nonnull
    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.CURRENTTEMPERATURE, currentTemp);
        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.MAXTEMPERATURE, maxTemp);
        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTADDEDHEAT, lastChange);
        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTTEMPERATURE, lastTemp);

        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTNEGATIVEINFLUENCE, lastNegativeTerm);
        compound.setFloat(References.NBTTagCompoundData.TE.ForgeBase.LASTPOSITIVEINFLUENCE, lastPositiveTerm);

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

    @Nonnull
    @Override
    public NBTBase writeToSynchronizationCompound() {
        return writeToNBTTagCompound();
    }

    public I getTileEntity() {
        return tileEntity;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void addLastChangeToCurrentTemp() {
        this.currentTemp += getLastChange();
    }

    public float getLastTemp() {
        return lastTemp;
    }

    public void setLastTemp(float lastTemp) {
        this.lastTemp = lastTemp;
    }

    public float getLastChange() {
        return lastChange;
    }

    public void setLastChange(float lastChange) {
        this.lastChange = lastChange;
    }

    public void addLastPositiveHeatTermToChange() {
        this.lastChange += lastPositiveTerm;
    }

    public void addLastNegativeHeatTermToChange() {
        this.lastChange -= lastNegativeTerm;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void changeMaxTemp(float change) {
        this.maxTemp += change;
    }

    public float getHeatedPercentage() {
        return (this.getCurrentTemp() / this.getMaxTemp());
    }

    public float getLastNegativeTerm() {
        return -1 * lastNegativeTerm;
    }

    public void setLastNegativeTerm(float lastNegativeTerm) {
        if (lastNegativeTerm < 0f)
            lastNegativeTerm *= -1;

        this.lastNegativeTerm = lastNegativeTerm;
    }

    public void changeLastNegativeTerm(float change) {
        this.lastNegativeTerm += change;
    }

    public float getLastPositiveTerm() {
        return lastPositiveTerm;
    }

    public void setLastPositiveTerm(float lastPositiveTerm) {
        if (lastPositiveTerm < 0f)
            lastPositiveTerm *= -1f;

        this.lastPositiveTerm = lastPositiveTerm;
    }

    public void changeLastPositiveTerm(float change) {
        this.lastPositiveTerm += change;
    }
}
