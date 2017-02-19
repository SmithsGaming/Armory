package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.common.fluid.IMoltenMetalAcceptor;
import com.smithsmodding.armory.api.common.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.BlockPump;
import com.smithsmodding.armory.common.block.types.EnumPumpType;
import com.smithsmodding.armory.common.tileentity.state.TileEntityPumpState;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public class TileEntityPump extends TileEntitySmithsCore<TileEntityPumpState, TileStorageBasedGUIManager> implements ITickable, IFluidContainingEntity {

    private final FluidTank internalTank = new FluidTank(References.General.FLUID_INGOT);
    private Integer delay = 20;

    public TileEntityPump() {
    }

    @Override
    protected TileStorageBasedGUIManager getInitialGuiManager() {
        return new TileStorageBasedGUIManager();
    }

    @Override
    protected TileEntityPumpState getInitialState() {
        return new TileEntityPumpState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Pump + "-" + getLocation().toString();
    }

    public EnumPumpType getType() {
        IBlockState state = getWorld().getBlockState(getPos());
        return state.getValue(BlockPump.TYPE);
    }

    public EnumFacing getFacing() {
        IBlockState state = getWorld().getBlockState(getPos());
        return state.getValue(BlockPump.DIRECTION);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);

        internalTank.readFromNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Pump.FLUIDS));

    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setTag(References.NBTTagCompoundData.TE.Pump.FLUIDS, internalTank.writeToNBT(new NBTTagCompound()));

        return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        delay --;

        if (delay != 0)
            return;

        if (getType() == EnumPumpType.VERTICAL)
            updateVertical();
        else
            updateHorizontal();

        delay = 20;
    }

    private void updateVertical() {
        EnumFacing facing = getFacing();
        TileEntity inputEntity = getWorld().getTileEntity(getPos().offset(facing));
        TileEntity outputEntity = getWorld().getTileEntity(getPos().offset(EnumFacing.UP));

        updateOutput(outputEntity, EnumFacing.DOWN);
        updateInput(inputEntity, facing.getOpposite());
    }

    private void updateHorizontal() {
        EnumFacing facing = getFacing();
        TileEntity inputEntity = getWorld().getTileEntity(getPos().offset(facing));
        TileEntity outputEntity = getWorld().getTileEntity(getPos().offset(facing.getOpposite()));

        updateOutput(outputEntity, facing);
        updateInput(inputEntity, facing.getOpposite());
    }

    private void updateInput(TileEntity inputEntity, EnumFacing inputFacing) {
        if (inputEntity == null)
            return;

        if (!inputEntity.hasCapability(ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY, inputFacing))
            return;

        IMoltenMetalProvider provider = inputEntity.getCapability(ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY, inputFacing);

        FluidStack simulatedDrain = provider.provideMetal(Integer.MAX_VALUE, true);
        if (simulatedDrain == null || simulatedDrain.amount == 0)
            return;

        int usedDrain = internalTank.fill(simulatedDrain, true);
        if (usedDrain == 0)
            return;

        provider.provideMetal(usedDrain, false);

        inputEntity.markDirty();
    }

    private void updateOutput(TileEntity outputEntity, EnumFacing outputFacing) {
        if (outputEntity == null)
            return;

        if (!outputEntity.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, outputFacing))
            return;

        IMoltenMetalAcceptor outputAcceptor = outputEntity.getCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, outputFacing);

        FluidStack simulatedOutputDrain = internalTank.drain(Integer.MAX_VALUE, false);
        if (simulatedOutputDrain == null || simulatedOutputDrain.amount == 0)
            return;

        FluidStack usedDrain = outputAcceptor.acceptMetal(simulatedOutputDrain, false);
        if (usedDrain == null || usedDrain.amount == 0)
            return;

        internalTank.drain(usedDrain, true);

        outputEntity.markDirty();
    }

    @Nonnull
    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        return internalTank;
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        return internalTank.getCapacity();
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        return internalTank.getFluidAmount();
    }
}
