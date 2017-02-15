package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class TileEntityRendererConduit extends TileEntitySpecialRenderer<TileEntityConduit> {


    @Override
    public void renderTileEntityAt(TileEntityConduit te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te == null)
            return;

        if (te.getType() == EnumConduitType.VERTICAL)
            return;

        if (te.getTotalTankSizeOnSide(null) == 0)
            return;

        double height = te.getTankContentsVolumeOnSide(null) / ((double) te.getTotalTankSizeOnSide(null)) * 0.7;
        FluidStack stack = te.getTankForSide(null).getFluid();
        if (stack == null)
            return;

        GlStateManager.pushMatrix();
        renderFluid(stack, te.getPos(), x, y, z, 0.1, 0.53750, 0.1, 0.9, 0.53750 + height, 0.9);
        GlStateManager.popMatrix();

        /*ConduitRenderComponents.getRenderer(null).render(stack, te.getPos(), x,y,z, height);

        for(EnumFacing facing : EnumFacing.HORIZONTALS)
            if (te.getConnectedSides().contains(facing))
                ConduitRenderComponents.getRenderer(facing).render(stack, te.getPos(), x, y, z, height);*/
    }

    private void renderFluid(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2) {
        RenderHelper.renderFluidCuboid(fluidStack, pos, x, y, z, x1, y1, z1, x2, y2, z2);
    }
}
