package com.smithsmodding.armory.client.render.tileentity;

import com.smithsmodding.armory.api.common.crafting.mixing.IFluidFluidToFluidMixingRecipe;
import com.smithsmodding.armory.client.render.tileentity.moltenmetalmixer.MoltenMetalMixerRenderComponents;
import com.smithsmodding.armory.common.block.BlockMoltenMetalMixer;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalMixer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Created by marcf on 3/9/2017.
 */
public class TileEntityRendererMoltenMetalMixer extends TileEntitySpecialRenderer<TileEntityMoltenMetalMixer> {

    @Override
    public void renderTileEntityAt(TileEntityMoltenMetalMixer te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te == null)
            return;

        for(EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (facing == BlockMoltenMetalMixer.getFacing(te.getWorld(), te.getPos()))
                continue;

            IFluidTank tank = te.getTankForSide(facing);
            if (tank == null)
                continue;

            double height = tank.getFluidAmount() / tank.getCapacity();
            if (height == 0)
                continue;

            MoltenMetalMixerRenderComponents.getRenderer(facing).render(tank.getFluid(), te.getPos(), x, y, z, 0);
        }

        if (te.getState().getCurrentRecipe() == null)
            return;

        IFluidFluidToFluidMixingRecipe recipe = te.getState().getCurrentRecipe();
        Float progression = te.getState().getCurrentProgress() / (float) recipe.getProcessingTime();

        MoltenMetalMixerRenderComponents.getRenderer(BlockMoltenMetalMixer.getFacing(te.getWorld(), te.getPos()))
                .renderCombined(recipe.getLeftInputStack(), recipe.getRightInputStack(), recipe.getExemplaryOutputStack(),
                        progression, te.getPos(), x,y,z, 0);
    }
}
