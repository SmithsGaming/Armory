package com.smithsmodding.armory.api.fluid;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public interface IMoltenMetalProvider {

    boolean canDrain(@Nonnull EnumFacing extractionDirection);

    FluidStack drainNext(int maxAmount, boolean doDrain, @Nonnull EnumFacing extractionDirection);
}
