package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalTank;
import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 7/28/2016.
 */
public class TileEntityRendererMoltenMetalTank extends TileEntitySpecialRenderer<TileEntityMoltenMetalTank> {

    @Override
    public void renderTileEntityAt(TileEntityMoltenMetalTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (true)
            return;

        if (te == null)
            return;

        double height = te.getTankContentsVolumeOnSide(null) / ((double) te.getTotalTankSizeOnSide(null)) * 0.7;
        FluidStack stack = te.getTankForSide(null).getFluid();
        if (stack == null)
            return;

        renderCenter(stack, te.getPos(), x, y, z, height);
    }

    private void renderCenter(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
        RenderHelper.renderFluidCuboid(fluidStack, pos, x, y, z, 0.150, 0.150, 0.150, 0.850, 0.150 + height, 0.850);
    }
}
