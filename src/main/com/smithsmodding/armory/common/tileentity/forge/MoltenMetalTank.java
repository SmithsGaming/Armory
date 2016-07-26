package com.smithsmodding.armory.common.tileentity.forge;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.smithscore.common.fluid.MultiFluidTank;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class MoltenMetalTank extends MultiFluidTank implements IMoltenMetalProvider {
    public MoltenMetalTank(int capacity) {
        super(capacity);
    }

    public MoltenMetalTank(int capacity, FluidStack... fluidStacks) {
        super(capacity, fluidStacks);
    }

    public MoltenMetalTank(int capacity, List<FluidStack> fluidStacks) {
        super(capacity, fluidStacks);
    }

    @Override
    public boolean canDrain() {
        return drainNext(Integer.MAX_VALUE, false) != null;
    }

    @Override
    public FluidStack drainNext(int maxAmount, boolean doDrain) {
        return drain(maxAmount, doDrain);
    }
}
