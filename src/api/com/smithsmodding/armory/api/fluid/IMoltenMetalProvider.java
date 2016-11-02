package com.smithsmodding.armory.api.fluid;

import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 11.10.2016)
 */
public interface IMoltenMetalProvider {

    FluidStack provideMetal(int amount, boolean simulate);
}
