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

        double height = te.getTankContentsVolumeOnSide(null) / ((double) te.getTotalTankSizeOnSide(null)) * 0.7;
        FluidStack stack = te.getTankForSide(null).getFluid();
        if (stack == null)
            return;

        ConduitRenderComponents.getRenderer(null).render(stack, te.getPos(), x,y,z, height);

        for(EnumFacing facing : EnumFacing.HORIZONTALS)
            if (te.getConnectedSides().contains(facing))
                ConduitRenderComponents.getRenderer(facing).render(stack, te.getPos(), x, y, z, height);
    }
}
