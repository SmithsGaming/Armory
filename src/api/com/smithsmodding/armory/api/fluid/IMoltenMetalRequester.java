package com.smithsmodding.armory.api.fluid;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public interface IMoltenMetalRequester {

    boolean canFill(FluidStack fluidStack, @Nonnull EnumFacing insertionDirection);

    int fillNext(FluidStack source, boolean doFill, @Nonnull EnumFacing extractionDirection);
}
