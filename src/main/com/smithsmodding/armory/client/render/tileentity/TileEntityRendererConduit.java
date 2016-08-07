package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class TileEntityRendererConduit extends TileEntitySpecialRenderer<TileEntityConduit> {

    @Override
    public void renderTileEntityAt(TileEntityConduit te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te == null)
            return;

        double height = te.getConduit().getFluidAmount() / ((double) te.getConduit().getCapacity()) * 0.04995;

        if (height < 0.00001)
            return;

        FluidStack stackToRender = te.getConduit().getFluid();
        if (stackToRender == null)
            return;

        for (EnumFacing facing : te.getConnectedSides()) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
                continue;

            switch (facing) {
                case NORTH: {
                    renderNorth(stackToRender, te.getPos(), x, y, z, height);
                    break;
                }
                case SOUTH: {
                    renderSouth(stackToRender, te.getPos(), x, y, z, height);
                    break;
                }
                case EAST: {
                    renderEast(stackToRender, te.getPos(), x, y, z, height);
                    break;
                }
                case WEST: {
                    renderWest(stackToRender, te.getPos(), x, y, z, height);
                    break;
                }
            }
        }

        renderCenter(stackToRender, te.getPos(), x, y, z, height);
    }

    private void renderCenter(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.60620, 0.39380, 0.53755 + height, 0.39380, EnumFacing.UP);
    }

    private void renderNorth(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0, EnumFacing.UP);
    }

    private void renderSouth(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.39380, 0.53755 + height, 0.60620, 0.60620, 0.53755 + height, 1, EnumFacing.UP);
    }

    private void renderWest(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0.60620, EnumFacing.UP);
    }

    private void renderEast(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 1, 0.53755 + height, 0.60620, EnumFacing.UP);
    }
}
