package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class TileEntityRendererConduit extends TileEntitySpecialRenderer<TileEntityConduit> {

    @Override
    public void renderTileEntityAt(TileEntityConduit te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te == null)
            return;

    }

    private void renderCenter(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.60620, 0.39380, 0.53755 + height, 0.39380, EnumFacing.UP);
    }

    private void renderNorth(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0, EnumFacing.UP);
    }

    private void renderSouth(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.39380, 0.53755 + height, 0.60620, 0.60620, 0.53755 + height, 1, EnumFacing.UP);
    }

    private void renderWest(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0.60620, EnumFacing.UP);
    }

    private void renderEast(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 1, 0.53755 + height, 0.60620, EnumFacing.UP);
    }
}
