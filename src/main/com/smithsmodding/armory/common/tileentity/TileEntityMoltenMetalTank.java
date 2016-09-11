package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityMoltenMetalTankGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityMoltenMetalTankState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class TileEntityMoltenMetalTank extends TileEntitySmithsCore<TileEntityMoltenMetalTankState, TileEntityMoltenMetalTankGuiManager> implements ITickable, IFluidContainingEntity {

    private ConduitFluidTank tank;
    private EnumTankType type;

    public TileEntityMoltenMetalTank() {
    }

    public TileEntityMoltenMetalTank(EnumTankType type) {
        this.tank = new ConduitFluidTank(type.getTankContents());
        this.type = type;
    }

    @Override
    protected TileEntityMoltenMetalTankGuiManager getInitialGuiManager() {
        return new TileEntityMoltenMetalTankGuiManager();
    }

    @Override
    protected TileEntityMoltenMetalTankState getInitialState() {
        return new TileEntityMoltenMetalTankState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Tank + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.type = EnumTankType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE));
        this.tank = new ConduitFluidTank(this.type.getTankContents());

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE, type.getMetadata());

        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        if (!(facing == EnumFacing.UP || facing == EnumFacing.DOWN) && (capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY))
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public Object getCapability(Capability capability, EnumFacing facing) {
        if (!(facing == EnumFacing.UP || facing == EnumFacing.DOWN) && (capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY))
            return tank;

        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {

    }

    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        return tank;
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        return tank.getCapacity();
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        return tank.getFluidAmount();
    }
}
