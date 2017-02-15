package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.client.render.tileentity.conduit.ConduitRenderComponents;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

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

        double height = te.getTankContentsVolumeOnSide(null) / ((double) te.getTotalTankSizeOnSide(null)) * 0.05;
        FluidStack stack = te.getTankForSide(null).getFluid();
        if (stack == null)
            return;

/*        GlStateManager.pushMatrix();
        RenderHelper.renderFluidCuboid(stack, te.getPos(), x, y, z, 0.1, 0.53750, 0.1, 0.9, 0.53750 + height, 0.9);
        GlStateManager.popMatrix();*/

        ConduitRenderComponents.getRenderer(null).render(stack, te.getPos(), x,y,z, height);

        for(EnumFacing facing : EnumFacing.HORIZONTALS)
            if (te.getConnectedSides().contains(facing))
                ConduitRenderComponents.getRenderer(facing).render(stack, te.getPos(), x, y, z, height);
    }
}
