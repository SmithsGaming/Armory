package com.smithsmodding.armory.common.tileentity.conduit;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class ConduitFluidTank implements IMoltenMetalProvider, IMoltenMetalRequester, IFluidTank, ITickable, INBTSerializable<NBTTagCompound> {

    private final int maxContents;
    private final int maxBufferTransferRate;

    FluidStack inputBuffer;
    FluidStack contents;
    FluidStack outputBuffer;

    public ConduitFluidTank(int maxContents, int maxBufferTransferRate) {
        this.maxContents = maxContents;
        this.maxBufferTransferRate = maxBufferTransferRate;
    }

    @Override
    public boolean canFill(FluidStack fluidStack) {
        if (inputBuffer == null && contents == null && outputBuffer == null)
            return true;

        if (inputBuffer != null) {
            if (inputBuffer.amount > 0)
                return false;

            return inputBuffer.amount + contents.amount + outputBuffer.amount < maxContents;
        }

        return false;
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return getTotalContents();
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

        if (inputBuffer == null && doFill) {
            inputBuffer = new FluidStack(resource, 0);
            contents = new FluidStack(resource, 0);
            outputBuffer = new FluidStack(resource, 0);
        }

        int usage = Math.min(resource.amount, maxContents - contents.amount - inputBuffer.amount - outputBuffer.amount);

        if (doFill)
            contents.amount += usage;

        return usage;
    }

    @Override
    public int fillNext(FluidStack source, boolean doFill) {
        if (!canFill(source))
            return 0;

        if (inputBuffer == null && doFill) {
            inputBuffer = new FluidStack(source, 0);
            contents = new FluidStack(source, 0);
            outputBuffer = new FluidStack(source, 0);
        }

        int usage = Math.min(source.amount, maxBufferTransferRate);

        if (doFill)
            inputBuffer.amount = usage;

        return usage;
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
                inputBuffer = null;
                contents = null;
                outputBuffer = null;
            }
        }

        return resultStank;
    }

    @Override
    public boolean canDrain() {
        return (outputBuffer != null && outputBuffer.amount > 0);
    }

    @Override
    public FluidStack drainNext(int maxAmount, boolean doDrain) {
        if (outputBuffer == null)
            return null;

        int usage = Math.min(outputBuffer.amount, maxAmount);

        FluidStack resultStank = new FluidStack(outputBuffer, usage);
        if (doDrain) {
            outputBuffer.amount -= usage;

            if (getTotalContents().amount <= 0) {
                inputBuffer = null;
                contents = null;
                outputBuffer = null;
            }
        }

        return resultStank;
    }

    public FluidStack getTotalContents() {
        if (contents == null)
            return null;

        return new FluidStack(contents, inputBuffer.amount + contents.amount + outputBuffer.amount);
    }

    @Override
    public void update() {
        if (inputBuffer == null)
            return;

        contents.amount += inputBuffer.amount;
        inputBuffer.amount = 0;

        if (outputBuffer.amount < maxBufferTransferRate && contents.amount > 0) {
            contents.amount -= (maxBufferTransferRate - outputBuffer.amount);
            outputBuffer.amount += (maxBufferTransferRate - outputBuffer.amount);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        if (inputBuffer == null)
            return compound;

        compound.setTag(References.NBTTagCompoundData.TE.Conduit.INPUT, inputBuffer.writeToNBT(new NBTTagCompound()));
        compound.setTag(References.NBTTagCompoundData.TE.Conduit.CONTENTS, contents.writeToNBT(new NBTTagCompound()));
        compound.setTag(References.NBTTagCompoundData.TE.Conduit.OUTPUT, outputBuffer.writeToNBT(new NBTTagCompound()));

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey(References.NBTTagCompoundData.TE.Conduit.INPUT)) {
            inputBuffer = null;
            contents = null;
            outputBuffer = null;

            return;
        }

        inputBuffer = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.INPUT));
        contents = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.CONTENTS));
        outputBuffer = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.OUTPUT));
    }

    public FluidStack getInputBuffer() {
        return inputBuffer;
    }

    public FluidStack getOutputBuffer() {
        return outputBuffer;
    }

    public FluidStack getContents() {
        return contents;
    }
}
