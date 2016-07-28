package com.smithsmodding.armory.common.tileentity.moltenmetal;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import com.smithsmodding.smithscore.common.fluid.MultiFluidTank;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class MoltenMetalTank extends MultiFluidTank implements IMoltenMetalProvider, IMoltenMetalRequester {

    private final Integer maxLiquidCount;

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

    @Override
    public boolean canDrain(@Nonnull EnumFacing extractionDirection) {
        return drainNext(Integer.MAX_VALUE, false, extractionDirection) != null;
    }

    @Override
    public FluidStack drainNext(int maxAmount, boolean doDrain, @Nonnull EnumFacing extractionDirection) {
        return drain(maxAmount, doDrain);
    }

    @Override
    public boolean canFill(FluidStack fluidStack, @Nonnull EnumFacing insertionDirection) {
        return fillNext(fluidStack, false, insertionDirection) > 0;
    }

    @Override
    public int fillNext(FluidStack source, boolean doFill, @Nonnull EnumFacing insertionDirection) {
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
}
