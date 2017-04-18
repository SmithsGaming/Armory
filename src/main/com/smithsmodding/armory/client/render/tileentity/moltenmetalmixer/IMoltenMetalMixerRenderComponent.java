package com.smithsmodding.armory.client.render.tileentity.moltenmetalmixer;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 3/9/2017.
 */
public interface IMoltenMetalMixerRenderComponent {

    void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height);


    void renderCombined(@Nonnull FluidStack left, @Nonnull FluidStack right, @Nonnull FluidStack output, @Nonnull Float progression, BlockPos pos, double x, double y, double z, double height);
}
