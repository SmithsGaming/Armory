package com.smithsmodding.armory.client.render.tileentity.moltenmetalmixer;

import com.smithsmodding.smithscore.util.client.RenderHelper;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 3/9/2017.
 */
public class MoltenMetalMixerRenderComponents {


    private static final HashMap<EnumFacing, IMoltenMetalMixerRenderComponent> renderer = new HashMap<>();

    static {
        renderer.put(EnumFacing.NORTH, new MoltenMetalMixerRenderComponents.MoltenMetalMixerRenderComponentNorth());
        renderer.put(EnumFacing.SOUTH, new MoltenMetalMixerRenderComponents.MoltenMetalMixerRenderComponentSouth());
        renderer.put(EnumFacing.WEST, new MoltenMetalMixerRenderComponents.MoltenMetalMixerRenderComponentWest());
        renderer.put(EnumFacing.EAST, new MoltenMetalMixerRenderComponents.MoltenMetalMixerRenderComponentEast());
    }

    @Nonnull
    public static IMoltenMetalMixerRenderComponent getRenderer(@Nonnull EnumFacing facing) {
        return renderer.get(facing);
    }

    public static class MoltenMetalMixerRenderComponentNorth implements IMoltenMetalMixerRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            renderFluidSide(fluidStack, pos, x, y, z, 0.45d, 0.25d, 0.15d, 0.55d, 0.785d, 0.3d, EnumFacing.NORTH);
        }

        @Override
        public void renderCombined(@Nonnull FluidStack left, @Nonnull FluidStack right, @Nonnull FluidStack output, @Nonnull Float progression, BlockPos pos, double x, double y, double z, double height) {
            GlStateManager.pushMatrix();
            MinecraftColor leftColor = new MinecraftColor(left.getFluid().getColor(left));
            MinecraftColor rightColor = new MinecraftColor(right.getFluid().getColor(right));
            MinecraftColor outputColor = new MinecraftColor(output.getFluid().getColor(output));
            
            leftColor = new MinecraftColor(leftColor.getRedFloat(), leftColor.getGreenFloat(), leftColor.getBlueFloat(), 1-progression);
            rightColor = new MinecraftColor(rightColor.getRedFloat(), rightColor.getGreenFloat(), rightColor.getBlueFloat(), 1-progression);
            outputColor = new MinecraftColor(outputColor.getRedFloat(), outputColor.getGreenFloat(), outputColor.getBlueFloat(), progression);

            renderFluidSide(left, pos, x, y, z, 0.45d, 0.25d, 0.15d, 0.55d, 0.5175, 0.3d, EnumFacing.NORTH, leftColor);
            renderFluidSide(right, pos, x, y, z, 0.45d, 0.5175d, 0.15d, 0.55d, 0.785, 0.3d, EnumFacing.NORTH, rightColor);
            renderFluidSide(output, pos, x, y, z, 0.45d, 0.25d, 0.15d, 0.55d, 0.785d, 0.3d, EnumFacing.NORTH, outputColor);

            GlStateManager.popMatrix();
        }
    }

    public static class MoltenMetalMixerRenderComponentSouth implements IMoltenMetalMixerRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            renderFluidSide(fluidStack, pos, x, y, z, 0.55d, 0.25d, 0.85d, 0.45d, 0.785d, 0.7d, EnumFacing.SOUTH);
        }

        @Override
        public void renderCombined(@Nonnull FluidStack left, @Nonnull FluidStack right, @Nonnull FluidStack output, @Nonnull Float progression, BlockPos pos, double x, double y, double z, double height) {
            GlStateManager.pushMatrix();
            MinecraftColor leftColor = new MinecraftColor(left.getFluid().getColor(left));
            MinecraftColor rightColor = new MinecraftColor(right.getFluid().getColor(right));
            MinecraftColor outputColor = new MinecraftColor(output.getFluid().getColor(output));

            leftColor = new MinecraftColor(leftColor.getRedFloat(), leftColor.getGreenFloat(), leftColor.getBlueFloat(), 1-progression);
            rightColor = new MinecraftColor(rightColor.getRedFloat(), rightColor.getGreenFloat(), rightColor.getBlueFloat(), 1-progression);
            outputColor = new MinecraftColor(outputColor.getRedFloat(), outputColor.getGreenFloat(), outputColor.getBlueFloat(), progression);

            renderFluidSide(left, pos, x, y, z, 0.55d, 0.25d, 0.85d, 0.45d, 0.5175d, 0.7d, EnumFacing.SOUTH, leftColor);
            renderFluidSide(right, pos, x, y, z, 0.55d, 0.5175d, 0.85d, 0.45d, 0.785d, 0.7d, EnumFacing.SOUTH, rightColor);
            renderFluidSide(output, pos, x, y, z, 0.55d, 0.25d, 0.85d, 0.45d, 0.785d, 0.7d, EnumFacing.SOUTH, outputColor);

            GlStateManager.popMatrix();
        }
    }

    public static class MoltenMetalMixerRenderComponentEast implements IMoltenMetalMixerRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            renderFluidSide(fluidStack, pos, x, y, z, 0.85d, 0.25d, 0.45d, 0.7d, 0.785d, 0.55d, EnumFacing.EAST);
        }

        @Override
        public void renderCombined(@Nonnull FluidStack left, @Nonnull FluidStack right, @Nonnull FluidStack output, @Nonnull Float progression, BlockPos pos, double x, double y, double z, double height) {
            GlStateManager.pushMatrix();
            MinecraftColor leftColor = new MinecraftColor(left.getFluid().getColor(left));
            MinecraftColor rightColor = new MinecraftColor(right.getFluid().getColor(right));
            MinecraftColor outputColor = new MinecraftColor(output.getFluid().getColor(output));

            leftColor = new MinecraftColor(leftColor.getRedFloat(), leftColor.getGreenFloat(), leftColor.getBlueFloat(), 1-progression);
            rightColor = new MinecraftColor(rightColor.getRedFloat(), rightColor.getGreenFloat(), rightColor.getBlueFloat(), 1-progression);
            outputColor = new MinecraftColor(outputColor.getRedFloat(), outputColor.getGreenFloat(), outputColor.getBlueFloat(), progression);

            renderFluidSide(left, pos, x, y, z, 0.85d, 0.25d, 0.45d, 0.7d, 0.5175d, 0.55d, EnumFacing.EAST, leftColor);
            renderFluidSide(right, pos, x, y, z, 0.85d, 0.5175d, 0.45d, 0.7d, 0.785d, 0.55d, EnumFacing.EAST, rightColor);
            renderFluidSide(output, pos, x, y, z, 0.85d, 0.25d, 0.45d, 0.7d, 0.785d, 0.55d, EnumFacing.EAST, outputColor);

            GlStateManager.popMatrix();
        }
    }

    public static class MoltenMetalMixerRenderComponentWest implements IMoltenMetalMixerRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            renderFluidSide(fluidStack, pos, x, y, z, 0.15d, 0.25d, 0.55d, 0.3d, 0.785d, 0.45, EnumFacing.WEST);
        }

        @Override
        public void renderCombined(@Nonnull FluidStack left, @Nonnull FluidStack right, @Nonnull FluidStack output, @Nonnull Float progression, BlockPos pos, double x, double y, double z, double height) {
            GlStateManager.pushMatrix();
            MinecraftColor leftColor = new MinecraftColor(left.getFluid().getColor(left));
            MinecraftColor rightColor = new MinecraftColor(right.getFluid().getColor(right));
            MinecraftColor outputColor = new MinecraftColor(output.getFluid().getColor(output));

            leftColor = new MinecraftColor(leftColor.getRedFloat(), leftColor.getGreenFloat(), leftColor.getBlueFloat(), 1-progression);
            rightColor = new MinecraftColor(rightColor.getRedFloat(), rightColor.getGreenFloat(), rightColor.getBlueFloat(), 1-progression);
            outputColor = new MinecraftColor(outputColor.getRedFloat(), outputColor.getGreenFloat(), outputColor.getBlueFloat(), progression);

            renderFluidSide(left, pos, x, y, z, 0.15d, 0.25d, 0.55d, 0.3d, 0.5175d, 0.45, EnumFacing.WEST, leftColor);
            renderFluidSide(right, pos, x, y, z, 0.15d, 0.5175d, 0.55d, 0.3d, 0.785d, 0.45, EnumFacing.WEST, rightColor);
            renderFluidSide(output, pos, x, y, z, 0.15d, 0.25d, 0.55d, 0.3d, 0.785d, 0.45, EnumFacing.WEST, outputColor);

            GlStateManager.popMatrix();
        }
    }

    private static void renderFluidSide(@Nonnull FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, @Nonnull EnumFacing facing) {
        RenderHelper.renderFluidCuboid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2);
    }

    private static void renderFluidSide(@Nonnull FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, @Nonnull EnumFacing facing, MinecraftColor color) {
        RenderHelper.renderFluidCuboid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2, color.getRGB());
    }
}
