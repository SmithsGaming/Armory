package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
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

        if (te.getStructure() == null)
            return;


        te.getWorld().theProfiler.startSection(getClass().getSimpleName());

        te.getWorld().theProfiler.startSection("StructureRetrieval");
        ConduitFluidTank tank = te.getStructure().getData().getStructureTank();
        te.getWorld().theProfiler.endSection();

        double height = tank.getFluidAmount() / ((double) tank.getCapacity()) * 0.04995;

        if (height < 0.00001) {
            te.getWorld().theProfiler.endSection();
            return;
        }

        FluidStack stackToRender = tank.getFluid();
        if (stackToRender == null) {
            te.getWorld().theProfiler.endSection();
            return;
        }

        te.getWorld().theProfiler.startSection("fluid");
        for (EnumFacing facing : te.getConnectedSides()) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
                continue;

            te.getWorld().theProfiler.startSection(facing.getName());

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

            te.getWorld().theProfiler.endSection();
        }

        te.getWorld().theProfiler.startSection("center");
        renderCenter(stackToRender, te.getPos(), x, y, z, height);
        te.getWorld().theProfiler.endSection();

        te.getWorld().theProfiler.endSection();

        te.getWorld().theProfiler.endSection();
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
