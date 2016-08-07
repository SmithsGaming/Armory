package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.util.common.MoltenMetalHelper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.BlockConduit;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityConduitGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityConduitState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class TileEntityConduit extends TileEntitySmithsCore<TileEntityConduitState, TileEntityConduitGuiManager> implements ITickable, IFluidContainingEntity {

    ConduitFluidTank conduit;
    int ticksUntilNextBalanceAttempt = 0;
    int failedAttempts = 0;

    ArrayList<EnumFacing> connectedSides = new ArrayList<>();

    public TileEntityConduit() {
        conduit = new ConduitFluidTank(300);
    }

    @Override
    protected TileEntityConduitGuiManager getInitialGuiManager() {
        return new TileEntityConduitGuiManager();
    }

    @Override
    protected TileEntityConduitState getInitialState() {
        return new TileEntityConduitState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Conduit + "-" + getLocation().toString();
    }

    public ConduitFluidTank getConduit() {
        return conduit;
    }

    @Override
    protected NBTBase writeFluidsToCompound() {
        return conduit.serializeNBT();
    }

    @Override
    protected void readFluidsFromCompound(NBTBase inventoryCompound) {
        if (inventoryCompound instanceof NBTTagList) {
            super.readFluidsFromCompound(inventoryCompound);
            return;
        }

        conduit.deserializeNBT((NBTTagCompound) inventoryCompound);
    }

    @Override
    public void update() {
        if (isRemote()) {
            return;
        }

        handleInternals();

        handlePullFromProviders();

        handlePushToRequesters();

        markDirty();
    }

    public void handleInternals() {
        if (ticksUntilNextBalanceAttempt > 0) {
            ticksUntilNextBalanceAttempt--;
            return;
        }

        int currentContents = conduit.getFluidAmount();
        int summedContents = currentContents;
        int countedComponents = 1;
        FluidStack source = conduit.getFluid();

        for (EnumFacing facing : EnumFacing.values()) {
            if (facing.getAxis().isVertical())
                continue;

            TileEntity entity = getWorld().getTileEntity(pos.offset(facing));
            IBlockState state = getWorld().getBlockState(pos.offset(facing)).getActualState(getWorld(), pos.offset(facing));
            if (entity instanceof TileEntityConduit && state.getValue(BlockConduit.TYPE) == getWorld().getBlockState(pos).getActualState(getWorld(), pos).getValue(BlockConduit.TYPE)) {
                TileEntityConduit tileEntityConduit = (TileEntityConduit) entity;

                countedComponents++;
                summedContents += tileEntityConduit.conduit.getFluidAmount();

                if (source == null)
                    source = tileEntityConduit.conduit.getFluid();
            }
        }

        int contentsPerComponent = summedContents / countedComponents;

        if (contentsPerComponent == 0 || source == null) {
            failedAttempts++;

            if (failedAttempts > 10) {
                ticksUntilNextBalanceAttempt = 10;
            }

            return;
        }

        failedAttempts = 0;

        for (EnumFacing facing : EnumFacing.values()) {
            if (facing.getAxis().isVertical())
                continue;

            TileEntity entity = getWorld().getTileEntity(pos.offset(facing));
            IBlockState state = getWorld().getBlockState(pos.offset(facing)).getActualState(getWorld(), pos.offset(facing));
            if (entity instanceof TileEntityConduit && state.getValue(BlockConduit.TYPE) == getWorld().getBlockState(pos).getActualState(getWorld(), pos).getValue(BlockConduit.TYPE)) {
                TileEntityConduit tileEntityConduit = (TileEntityConduit) entity;

                tileEntityConduit.conduit.setFluid(new FluidStack(source, contentsPerComponent));
            }
        }

        conduit.setFluid(new FluidStack(source, contentsPerComponent));

        TileEntity entityDown = getWorld().getTileEntity(getPos().offset(EnumFacing.DOWN));
        IBlockState state = getWorld().getBlockState(pos.offset(EnumFacing.DOWN)).getActualState(getWorld(), pos.offset(EnumFacing.DOWN));
        if (entityDown instanceof TileEntityConduit && state.getValue(BlockConduit.TYPE) == getWorld().getBlockState(pos).getActualState(getWorld(), pos).getValue(BlockConduit.TYPE)) {
            MoltenMetalHelper.transferMaxAmount(conduit, ((TileEntityConduit) entityDown).conduit);
        }
    }

    public void handlePushToRequesters() {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.UP)
                continue;

            TileEntity entity = getWorld().getTileEntity(getPos().offset(facing));

            if (entity == null || !entity.hasCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite()))
                continue;

            MoltenMetalHelper.transferMaxAmount(conduit, entity.getCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite()));
            entity.markDirty();
        }
    }

    public void handlePullFromProviders() {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.UP)
                continue;

            TileEntity entity = getWorld().getTileEntity(getPos().offset(facing));

            if (entity == null || !entity.hasCapability(ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY, facing.getOpposite()))
                continue;

            IMoltenMetalProvider provider = entity.getCapability(ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY, facing.getOpposite());
            FluidStack sim = provider == null ? null : provider.drainNext(Integer.MAX_VALUE, false);

            MoltenMetalHelper.transferMaxAmount(entity.getCapability(ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY, facing.getOpposite()), conduit);
        }
    }

    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        return conduit;
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        return conduit.getCapacity();
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        return conduit.getFluidAmount();
    }

    public ArrayList<EnumFacing> getConnectedSides() {
        return connectedSides;
    }

    public void setConnectedSides(ArrayList<EnumFacing> connectedSides) {
        this.connectedSides = connectedSides;
    }
}
