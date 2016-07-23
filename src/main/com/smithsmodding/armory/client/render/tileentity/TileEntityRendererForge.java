package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 22.07.2016)
 */
public class TileEntityRendererForge extends TileEntitySpecialRenderer<TileEntityForge> {

    @Override
    public void renderTileEntityAt(TileEntityForge te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te == null)
            return;

        BlockPos pos = new BlockPos(x, y, z);

        if (te.getStructure() == null)
            return;

        FluidStack stackToRender = te.getStructure().getData().getMoltenMetals().getFluid();
        if (stackToRender == null)
            return;

        double height = te.getTankContentsVolumeOnSide(null) / ((double) te.getTotalTankSizeOnSide(null)) * 0.005d;

        GlStateManager.pushMatrix();
        renderFluid(stackToRender, pos, x, y, z, 0.1, 0.53750, 0.1, 0.9, 0.53750 + height, 0.9);
        GlStateManager.popMatrix();
    }

    private void renderFluid(FluidStack fluidStack, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2) {
        RenderHelper.renderFluidCuboid(fluidStack, pos, x, y, z, x1, y1, z1, x2, y2, z2);
    }
}
