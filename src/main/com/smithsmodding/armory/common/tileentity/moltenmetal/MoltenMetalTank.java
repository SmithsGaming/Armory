package com.smithsmodding.armory.common.tileentity.moltenmetal;

import com.smithsmodding.armory.api.common.fluid.IMoltenMetalAcceptor;
import com.smithsmodding.armory.api.common.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.common.fluid.MultiFluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class MoltenMetalTank extends MultiFluidTank implements IMoltenMetalAcceptor, IMoltenMetalProvider {

    private Integer maxLiquidCount;

    public MoltenMetalTank(int capacity, Integer maxLiquidCount) {
        super(capacity);
        this.maxLiquidCount = maxLiquidCount;
    }

    public MoltenMetalTank(int capacity, Integer maxLiquidCount, FluidStack... fluidStacks) {
        super(capacity, fluidStacks);
        this.maxLiquidCount = maxLiquidCount;
    }

    public MoltenMetalTank(int capacity, Integer maxLiquidCount, List<FluidStack> fluidStacks) {
        super(capacity, fluidStacks);
        this.maxLiquidCount = maxLiquidCount;
    }

    public boolean canDrain() {
        return drainNext(Integer.MAX_VALUE, false) != null;
    }

    @Nullable
    public FluidStack drainNext(int maxAmount, boolean doDrain) {
        return drain(maxAmount, doDrain);
    }

    //@Override
    public boolean canFill(@Nonnull FluidStack fluidStack, @Nonnull EnumFacing insertionDirection) {
        return fillNext(fluidStack, false, insertionDirection) > 0;
    }

    //@Override
    public int fillNext(@Nonnull FluidStack source, boolean doFill, @Nonnull EnumFacing insertionDirection) {
        return fill(source, doFill);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (getFluidStacks().size() == maxLiquidCount) {
            boolean foundMatching = false;

            for (FluidStack stank : getFluidStacks()) {
                if (stank.isFluidEqual(resource)) {
                    foundMatching = true;
                }
            }

            if (!foundMatching)
                return 0;
        }

        return super.fill(resource, doFill);
    }

    @Override
    public FluidStack acceptMetal(FluidStack metal, boolean simulate) {
        return new FluidStack(metal.getFluid(), fill(metal, !simulate), metal.tag);
    }

    @Override
    public FluidStack provideMetal(int amount, boolean simulate) {
        return drain(amount, !simulate);
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound superCompound = super.serializeNBT();

        superCompound.setInteger(References.NBTTagCompoundData.Fluids.MoltenMetalTank.MAXLIQUIDCOUNT, maxLiquidCount);
        return superCompound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        maxLiquidCount = nbt.getInteger(References.NBTTagCompoundData.Fluids.MoltenMetalTank.MAXLIQUIDCOUNT);
    }
}
