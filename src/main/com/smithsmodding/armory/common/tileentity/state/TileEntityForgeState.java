package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForgeState extends TileEntityForgeBaseState<TileEntityForge> {

    private float mixingProgress = 0;
    private ArrayList<Float> meltingProgress = new ArrayList<Float>();

    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {
        super.onStateCreated(tileEntitySmithsCore);

        meltingProgress = new ArrayList<>();
        for (int i = 0; i < TileEntityForge.INGOTSTACKS_AMOUNT; i++) {
            meltingProgress.add(0f);
        }
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {
        super.readFromNBTTagCompound(stateData);

        NBTTagCompound compound = (NBTTagCompound) stateData;

        mixingProgress = compound.getFloat(References.NBTTagCompoundData.TE.Forge.MIXINGPROGRESS);

        NBTTagCompound meltingCompound = compound.getCompoundTag(References.NBTTagCompoundData.TE.Forge.MELTINGPROGRESS);
        for (int i = 0; i < TileEntityForge.INGOTSTACKS_AMOUNT; i++) {
            meltingProgress.set(i, meltingCompound.getFloat(String.valueOf(i)));
        }
    }

    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = (NBTTagCompound) super.writeToNBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.Forge.MIXINGPROGRESS, mixingProgress);

        NBTTagCompound meltingCompound = new NBTTagCompound();
        for (int i = 0; i < TileEntityForge.INGOTSTACKS_AMOUNT; i++) {
            meltingCompound.setFloat(String.valueOf(i), meltingProgress.get(i));
        }

        compound.setTag(References.NBTTagCompoundData.TE.Forge.MELTINGPROGRESS, meltingCompound);

        return compound;
    }

    public float getMixingProgress() {
        return mixingProgress;
    }

    public void setMixingProgress(float mixingProgress) {
        this.mixingProgress = mixingProgress;
    }

    public float getMeltingProgess(int slotIndex) {
        if (slotIndex >= TileEntityForge.INGOTSTACKS_AMOUNT || slotIndex < 0)
            return -1F;

        return meltingProgress.get(slotIndex);
    }

    public void setMeltingProgress(int slotIndex, float progress) {
        if (slotIndex >= TileEntityForge.INGOTSTACKS_AMOUNT || slotIndex < 0)
            return;

        meltingProgress.set(slotIndex, progress);
    }
}
