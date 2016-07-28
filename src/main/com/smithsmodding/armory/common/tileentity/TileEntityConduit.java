package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
import com.smithsmodding.armory.common.tileentity.conduit.IConduitTankProvider;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityConduitGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityConduitState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class TileEntityConduit extends TileEntitySmithsCore<TileEntityConduitState, TileEntityConduitGuiManager> implements ITickable, IFluidContainingEntity, IConduitTankProvider {

    ConduitFluidTank conduit;
    boolean shouldUpdateState = true;

    public TileEntityConduit() {
        conduit = new ConduitFluidTank(this, 300, 150);
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
    public void update() {
        if (isRemote())
            return;

        conduit.update();

        shouldUpdateState = !shouldUpdateState;
        if (shouldUpdateState)
            getState().onStateUpdated();

        handleInternals();

        markDirty();
    }

    public void handleInternals() {
        TileEntity north = getWorld().getTileEntity(getPos().north());
        TileEntity east = getWorld().getTileEntity(getPos().east());
        TileEntity south = getWorld().getTileEntity(getPos().south());
        TileEntity west = getWorld().getTileEntity(getPos().west());
        TileEntity up = getWorld().getTileEntity(getPos().up());
        TileEntity down = getWorld().getTileEntity(getPos().down());

        pushOutputForSide(north, conduit, EnumFacing.NORTH);
        pushOutputForSide(east, conduit, EnumFacing.EAST);
        pushOutputForSide(south, conduit, EnumFacing.SOUTH);
        pushOutputForSide(west, conduit, EnumFacing.WEST);
        pushOutputForSide(up, conduit, EnumFacing.UP);
        pushOutputForSide(down, conduit, EnumFacing.DOWN);
    }

    public void pushOutputForSide(TileEntity entity, ConduitFluidTank sourceTank, EnumFacing facing) {
        if (entity == null || sourceTank == null)
            return;

        if (entity.hasCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite())) {
            IMoltenMetalRequester requester = entity.getCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite());
            if (requester == null)
                return;

            FluidStack simmedDrain = sourceTank.drainNext(Integer.MAX_VALUE, false, facing.getOpposite());
            if (simmedDrain == null)
                return;

            int simmedFill = requester.fillNext(simmedDrain, false, facing.getOpposite());

            if (simmedDrain.amount < simmedFill)
                simmedFill = simmedDrain.amount;
            if (simmedFill < simmedDrain.amount)
                simmedDrain.amount = simmedFill;

            requester.fillNext(simmedDrain, true, facing.getOpposite());
            sourceTank.drainNext(simmedFill, true, facing.getOpposite());
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY) {
            return (T) conduit;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canExtractFrom(EnumFacing direction) {
        return getState().canExtractFromDirection(direction);
    }

    @Override
    public boolean canInsertFrom(EnumFacing direction) {
        return getState().canInsertFromDirection(direction);
    }

    @Override
    public void onExtractionFrom(EnumFacing direction) {
        getState().onExtraction(direction);
    }

    @Override
    public void onInsertionFrom(EnumFacing direction) {
        getState().onInsertion(direction);
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
}
