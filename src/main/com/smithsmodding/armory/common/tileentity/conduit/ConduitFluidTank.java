package com.smithsmodding.armory.common.tileentity.conduit;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class ConduitFluidTank implements IMoltenMetalProvider, IMoltenMetalRequester, IFluidTank, INBTSerializable<NBTTagCompound> {

    FluidStack contents;
    private int maxContents;

    public ConduitFluidTank(int maxContents) {
        this.maxContents = maxContents;
    }

    @Override
    public boolean canFill(FluidStack fluidStack) {
        if (contents == null)
            return true;

        if (contents != null) {
            return contents.amount < maxContents;
        }

        return fluidStack.isFluidEqual(contents);
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return getTotalContents();
    }

    public void setFluid(FluidStack stack) {
        if (stack == null) {
            this.contents = null;
            return;
        }

        this.contents = stack.copy();
    }

    @Override
    public int getFluidAmount() {
        if (getTotalContents() == null)
            return 0;

        return getTotalContents().amount;
    }

    @Override
    public int getCapacity() {
        return maxContents;
    }

    @Override
    public FluidTankInfo getInfo() {
        return null;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (getFluidAmount() == maxContents)
            return 0;

        if (!getFluid().isFluidEqual(resource))
            return 0;

        if (contents == null && doFill) {
            contents = new FluidStack(resource, 0);
        }

        int usage = Math.min(resource.amount, maxContents - contents.amount);

        if (doFill)
            contents.amount += usage;

        return usage;
    }

    @Override
    public int fillNext(FluidStack source, boolean doFill) {
        if (!canFill(source))
            return 0;

        if (contents == null && doFill) {
            contents = new FluidStack(source, 0);
        }

        int usage = Math.min(source.amount, maxContents - (contents == null ? 0 : contents.amount));

        if (doFill) {
            contents.amount += usage;
        }
        return usage;
    }

    public FluidStack getCurrentContents() {
        return contents;
    }

    public void setCurrentContents(FluidStack newContents) {
        this.contents = newContents;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (contents == null)
            return null;

        int usage = Math.min(contents.amount, maxDrain);
        FluidStack resultStank = new FluidStack(contents, usage);

        if (doDrain) {
            contents.amount -= usage;

            if (getTotalContents().amount <= 0) {
                contents = null;
            }
        }

        return resultStank;
    }

    @Override
    public boolean canDrain() {
        return (contents != null && contents.amount > 0);
    }

    @Override
    public FluidStack drainNext(int maxAmount, boolean doDrain) {
        if (contents == null || !canDrain())
            return null;

        int usage = Math.min(contents.amount, maxAmount);

        FluidStack resultStank = new FluidStack(contents, usage);
        if (doDrain) {
            contents.amount -= usage;

            if (getTotalContents().amount <= 0) {
                contents = null;
            }
        }

        return resultStank;
    }

    public FluidStack getTotalContents() {
        if (contents == null)
            return null;

        return contents.copy();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        if (contents == null)
            return compound;

        compound.setTag(References.NBTTagCompoundData.TE.Conduit.CONTENTS, contents.writeToNBT(new NBTTagCompound()));

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey(References.NBTTagCompoundData.TE.Conduit.CONTENTS)) {
            contents = null;

            return;
        }

        contents = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.CONTENTS));
    }

    public FluidStack getContents() {
        return contents;
    }

    public void updateSize(int newSize) {
        this.maxContents = newSize;
        if (contents != null && contents.amount > newSize)
            contents.amount = newSize;
    }
}
