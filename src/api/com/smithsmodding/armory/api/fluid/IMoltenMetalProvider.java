package com.smithsmodding.armory.api.fluid;

import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public interface IMoltenMetalProvider {

    boolean canDrain();

    FluidStack drainNext(int maxAmount, boolean doDrain);
}
