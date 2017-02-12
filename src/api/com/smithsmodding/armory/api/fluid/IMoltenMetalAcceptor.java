package com.smithsmodding.armory.api.fluid;

import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public interface IMoltenMetalAcceptor {

    FluidStack acceptMetal(FluidStack metel, boolean simulate);
}
