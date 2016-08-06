package com.smithsmodding.armory.api.fluid;

import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public interface IMoltenMetalRequester {

    boolean canFill(FluidStack fluidStack);

    int fillNext(FluidStack source, boolean doFill);
}
