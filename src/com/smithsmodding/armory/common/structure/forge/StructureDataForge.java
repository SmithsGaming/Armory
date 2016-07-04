package com.smithsmodding.armory.common.structure.forge;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import com.smithsmodding.smithscore.util.common.FluidStackHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureDataForge implements IStructureData<StructureForge>, com.smithsmodding.armory.common.tileentity.state.IForgeFuelDataContainer {

    boolean isBurning;

    int totalBurningTicksOnCurrentFuel;
    int burningTicksLeftOnCurrentFuel;

    private ArrayList<FluidStack> moltenMetals = new ArrayList<FluidStack>();

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setBoolean(References.NBTTagCompoundData.TE.ForgeBase.CURRENTLYBURNING, isBurning);
        compound.setInteger(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT, totalBurningTicksOnCurrentFuel);
        compound.setInteger(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME, burningTicksLeftOnCurrentFuel);

        NBTTagList fluidCompound = new NBTTagList();
        for (FluidStack fluidStack : moltenMetals) {
            fluidCompound.appendTag(fluidStack.writeToNBT(new NBTTagCompound()));
        }

        compound.setTag(References.NBTTagCompoundData.TE.Forge.Structure.FLUIDS, fluidCompound);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        setBurning(compound.getBoolean(References.NBTTagCompoundData.TE.ForgeBase.CURRENTLYBURNING));
        setTotalBurningTicksOnCurrentFuel(compound.getInteger(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKFUELAMOUNT));
        setBurningTicksLeftOnCurrentFuel(compound.getInteger(References.NBTTagCompoundData.TE.ForgeBase.FUELSTACKBURNINGTIME));

        moltenMetals.clear();
        NBTTagList fluidCompound = compound.getTagList(References.NBTTagCompoundData.TE.Forge.Structure.FLUIDS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < fluidCompound.tagCount(); i++) {
            moltenMetals.add(FluidStack.loadFluidStackFromNBT(fluidCompound.getCompoundTagAt(i)));
        }
    }

    @Override
    public void onDataMergeInto(IStructureData<StructureForge> otherData) {
        if (!(otherData instanceof StructureDataForge))
            return;

        StructureDataForge dataForge = (StructureDataForge) otherData;
        if (!isBurning())
            setBurning(dataForge.isBurning());

        changeTotalBurningTicksOnCurrentFuel(dataForge.getTotalBurningTicksOnCurrentFuel());
        changeBurningTicksLeftOnCurrentFuel(dataForge.getBurningTicksLeftOnCurrentFuel());

        for (FluidStack fluidStack : dataForge.getMoltenMetals()) {
            addLiquidToContainer(fluidStack);
        }
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
    public boolean isBurning() {
        return isBurning;
    }

    @Override
    public void setBurning(boolean burning) {
        isBurning = burning;
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
        this.totalBurningTicksOnCurrentFuel += change;
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
        this.burningTicksLeftOnCurrentFuel += change;
    }

    @Override
    public void resetBurningTicksLeftOnCurrentFuel() {
        setBurningTicksLeftOnCurrentFuel(getTotalBurningTicksOnCurrentFuel());
    }

    public ArrayList<FluidStack> getMoltenMetals() {
        return moltenMetals;
    }

    public void setMoltenMetals(ArrayList<FluidStack> moltenMetals) {
        this.moltenMetals = moltenMetals;
    }
}
