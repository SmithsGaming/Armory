package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.common.structures.*;
import com.smithsmodding.smithscore.common.tileentity.*;
import com.smithsmodding.smithscore.common.tileentity.state.*;
import net.minecraft.nbt.*;

import java.util.*;

/**
 * Created by Marc on 20.12.2015.
 */
public class FirePitState implements ITileEntityState, IStructureData {

    private TileEntitySmithsCore parent;

    private float totalBurningTicksLeft = 0F;
    private float totalBurningTicks = 0F;

    private boolean isBurning = false;
    private float maxTemperature = 1500;
    private float currentTemperature = 20;
    private float lastTemperature = 20;
    private float lastAddedHeat = 0;

    private float mixingProgress = 0;

    private ArrayList<Float> meltingProgress = new ArrayList<Float>();

    public FirePitState () {
        this(0F, 0F);
    }

    public FirePitState (float pBurningTicksLeft, float pTotalBurningTicks) {
        totalBurningTicksLeft = pBurningTicksLeft;
        totalBurningTicks = pTotalBurningTicks;

        for (int i = 0; i < TileEntityFirePit.INGOTSTACKS_AMOUNT; i++) {
            meltingProgress.add(-1F);
        }
    }

    @Override
    public Object getData (IStructureComponent requestingComponent, String propertyRequested) {
        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) && ( (TileEntityFirePit) parent ).isSlaved() && ( (TileEntityFirePit) parent ).getStructureData() != null)
            return ( (TileEntityFirePit) parent ).getStructureData().getData(requestingComponent, propertyRequested);

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT) && ( (TileEntityFirePit) parent ).isSlaved() && ( (TileEntityFirePit) parent ).getStructureData() != null)
            return ( (TileEntityFirePit) parent ).getStructureData().getData(requestingComponent, propertyRequested);

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            return totalBurningTicksLeft;

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            return totalBurningTicks;

        return 0F;
    }

    @Override
    public void setData (IStructureComponent sendingComponent, String propertySend, Object data) {
        if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) && ( (TileEntityFirePit) parent ).isSlaved())
            ( (TileEntityFirePit) parent ).getStructureData().setData(sendingComponent, propertySend, data);

        if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT) && ( (TileEntityFirePit) parent ).isSlaved())
            ( (TileEntityFirePit) parent ).getStructureData().setData(sendingComponent, propertySend, data);

        if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            totalBurningTicksLeft = (Float) data;
        else if (propertySend.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            totalBurningTicks = (Float) data;
        else
            return;
    }


    @Override
    public void onStateCreated (TileEntitySmithsCore tileEntitySmithsCore) {
        parent = tileEntitySmithsCore;
    }

    @Override
    public void onStateUpdated () {
    }

    @Override
    public void onStateDestroyed () {
        return;
    }

    @Override
    public boolean requiresNBTStorage () {
        return true;
    }

    @Override
    public void readFromNBTTagCompound (NBTBase stateData) {
        NBTTagCompound compound = (NBTTagCompound) stateData;

        totalBurningTicksLeft = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);
        totalBurningTicks = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);

        isBurning = compound.getBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING);
        currentTemperature = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE);
        maxTemperature = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE);
        lastAddedHeat = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT);
        lastTemperature = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.LASTTEMPERATURE);

        mixingProgress = compound.getFloat(References.NBTTagCompoundData.TE.FirePit.MIXINGPROGRESS);

        NBTTagCompound meltingCompound = compound.getCompoundTag(References.NBTTagCompoundData.TE.FirePit.MELTINGPROGRESS);
        for (int i = 0; i < TileEntityFirePit.INGOTSTACKS_AMOUNT; i++) {
            meltingProgress.set(i, meltingCompound.getFloat(String.valueOf(i)));
        }
    }

    @Override
    public NBTBase writeToNBTTagCompound () {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, totalBurningTicksLeft);
        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, totalBurningTicks);

        compound.setBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING, isBurning);
        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE, currentTemperature);
        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE, maxTemperature);
        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT, lastAddedHeat);
        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.LASTTEMPERATURE, lastTemperature);

        compound.setFloat(References.NBTTagCompoundData.TE.FirePit.MIXINGPROGRESS, mixingProgress);

        NBTTagCompound meltingCompound = new NBTTagCompound();
        for (int i = 0; i < TileEntityFirePit.INGOTSTACKS_AMOUNT; i++) {
            meltingCompound.setFloat(String.valueOf(i), meltingProgress.get(i));
        }

        compound.setTag(References.NBTTagCompoundData.TE.FirePit.MELTINGPROGRESS, meltingCompound);

        return compound;
    }

    @Override
    public boolean requiresSynchronization () {
        return true;
    }

    @Override
    public void readFromSynchronizationCompound (NBTBase stateData) {
        readFromNBTTagCompound(stateData);
    }

    @Override
    public NBTBase writeToSynchronizationCompound () {
        return writeToNBTTagCompound();
    }

    public boolean isBurning () {
        return isBurning;
    }

    public void setBurning (boolean burning) {
        isBurning = burning;
    }

    public float getLastAddedHeat () {
        return lastAddedHeat;
    }

    public void setLastAddedHeat (float lastAddedHeat) {
        this.lastAddedHeat = lastAddedHeat;
    }

    public float getLastTemperature () {
        return lastTemperature;
    }

    public void setLastTemperature (float lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    public float getMaxTemperature () {
        return maxTemperature;
    }

    public void setMaxTemperature (float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getCurrentTemperature () {
        return currentTemperature;
    }

    public void setCurrentTemperature (float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getMixingProgress () {
        return mixingProgress;
    }

    public void setMixingProgress (float mixingProgress) {
        this.mixingProgress = mixingProgress;
    }

    public float getMeltingProgess (int slotIndex) {
        if (slotIndex >= TileEntityFirePit.INGOTSTACKS_AMOUNT || slotIndex < 0)
            return -1F;

        return meltingProgress.get(slotIndex);
    }

    public void setMeltingProgress (int slotIndex, float progress) {
        if (slotIndex >= TileEntityFirePit.INGOTSTACKS_AMOUNT || slotIndex < 0)
            return;

        meltingProgress.set(slotIndex, progress);
    }
}
