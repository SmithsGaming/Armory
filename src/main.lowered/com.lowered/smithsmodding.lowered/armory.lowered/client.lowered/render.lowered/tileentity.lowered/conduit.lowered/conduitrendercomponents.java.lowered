package com.smithsmodding.armory.client.render.tileentity.conduit;

import com.smithsmodding.smithscore.util.client.RenderHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by marcf on 2/8/2017.
 */
public class ConduitRenderComponents {

    private static final HashMap<EnumFacing, IConduitRenderComponent> renderer = new HashMap<>();

    static {
        renderer.put(null, new ConduitRenderComponentCenter());
        renderer.put(EnumFacing.NORTH, new ConduitRenderComponentNorth());
        renderer.put(EnumFacing.SOUTH, new ConduitRenderComponentSouth());
        renderer.put(EnumFacing.WEST, new ConduitRenderComponentWest());
        renderer.put(EnumFacing.EAST, new ConduitRenderComponentEast());
    }

    @Nonnull
    public static IConduitRenderComponent getRenderer(@Nullable EnumFacing facing) {
        return renderer.get(facing);
    }

    public static class ConduitRenderComponentCenter implements IConduitRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            ConduitRenderComponents.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.60620, 0.39380, 0.53755 + height, 0.39380, EnumFacing.UP);
        }
    }

    public static class ConduitRenderComponentNorth implements IConduitRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            ConduitRenderComponents.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0, EnumFacing.UP);
        }
    }

    public static class ConduitRenderComponentSouth implements IConduitRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            ConduitRenderComponents.renderFluidSide(fluidStack, pos, x, y, z, 0.39380, 0.53755 + height, 0.60620, 0.60620, 0.53755 + height, 1, EnumFacing.UP);
        }
    }

    public static class ConduitRenderComponentWest implements IConduitRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            ConduitRenderComponents.renderFluidSide(fluidStack, pos, x, y, z, 0, 0.53755 + height, 0.39380, 0.39380, 0.53755 + height, 0.60620, EnumFacing.UP);
        }
    }

    public static class ConduitRenderComponentEast implements IConduitRenderComponent {

        @Override
        public void render(@Nonnull FluidStack fluidStack, BlockPos pos, double x, double y, double z, double height) {
            ConduitRenderComponents.renderFluidSide(fluidStack, pos, x, y, z, 0.60620, 0.53755 + height, 0.39380, 1, 0.53755 + height, 0.60620, EnumFacing.UP);
        }
    }

    private static void renderFluidSide(@Nonnull FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, @Nonnull EnumFacing facing) {
        final double yDelta = 0.66;

        x1 += (x);
        y1 += (y - yDelta);
        z1 += (z);
        x2 += (x);
        y2 += (y - yDelta);
        z2 += (z);


        RenderHelper.renderFluidSide(fluid, pos, 0, 0, 0, x1, y1, z1, x2, y2, z2, facing);
    }
}
