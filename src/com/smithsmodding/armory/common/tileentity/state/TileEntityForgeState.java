package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.structures.IStructureComponent;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.util.common.FluidStackHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForgeState extends TileEntityForgeBaseState<TileEntityForge> implements IStructureData<TileEntityForge> {

    private float mixingProgress = 0;
    private ArrayList<FluidStack> moltenMetals = new ArrayList<FluidStack>();
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
    public Object getData(IStructureComponent requestingComponent, String propertyRequested) {
        if (propertyRequested.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME) && getTileEntity().isSlaved() && getTileEntity().getStructureData() != null)
            return getTileEntity().getStructureData().getData(requestingComponent, propertyRequested);

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT) && getTileEntity().isSlaved() && getTileEntity().getStructureData() != null)
            return getTileEntity().getStructureData().getData(requestingComponent, propertyRequested);

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME))
            return getTotalBurningTicksOnCurrentFuel();

        if (propertyRequested.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT))
            return getBurningTicksLeftOnCurrentFuel();

        return 0F;
    }

    @Override
    public void setData(IStructureComponent sendingComponent, String propertySend, Object data) {
        if (propertySend.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME) && getTileEntity().isSlaved())
            getTileEntity().getStructureData().setData(sendingComponent, propertySend, data);

        if (propertySend.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT) && getTileEntity().isSlaved())
            getTileEntity().getStructureData().setData(sendingComponent, propertySend, data);

        if (propertySend.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME))
            setTotalBurningTicksOnCurrentFuel((Integer) data);
        else if (propertySend.equals(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT))
            setBurningTicksLeftOnCurrentFuel((Integer) data);
        else
            return;
    }

    @Override
    public void onDataMergeInto(IStructureData<TileEntityForge> data) {
        TileEntityForgeState state = (TileEntityForgeState) data;

        changeTotalBurningTicksOnCurrentFuel(state.getTotalBurningTicksOnCurrentFuel());
        changeBurningTicksLeftOnCurrentFuel(state.getBurningTicksLeftOnCurrentFuel());

        if (!this.isBurning())
            this.setBurning(state.isBurning());

        this.mixingProgress = 0f;

        state.getMoltenMetals().forEach(this::addLiquidToContainer);
    }

    private void addLiquidToContainer(FluidStack stack) {
        for (FluidStack containingStack : moltenMetals) {
            if (FluidStackHelper.equalsIgnoreStackSize(stack, containingStack)) {
                containingStack.amount += stack.amount;
                return;
            }
        }

        moltenMetals.add(stack);
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

    public ArrayList<FluidStack> getMoltenMetals() {
        return moltenMetals;
    }

    public void setMoltenMetals(ArrayList<FluidStack> moltenMetals) {
        this.moltenMetals = moltenMetals;
    }
}
