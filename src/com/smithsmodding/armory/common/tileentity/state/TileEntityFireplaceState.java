package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityFireplaceState extends TileEntityForgeBaseState<TileEntityFireplace> {

    private float cookingProgress = 0f;
    private float cookingSpeedMultiplier = 1f;

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
}
