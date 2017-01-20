package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityMoltenMetalTankGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityMoltenMetalTankState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.IFluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class TileEntityMoltenMetalTank extends TileEntitySmithsCore<TileEntityMoltenMetalTankState, TileEntityMoltenMetalTankGuiManager> implements ITickable, IFluidContainingEntity {

    private EnumTankType type;

    public TileEntityMoltenMetalTank() {
    }

    public TileEntityMoltenMetalTank(@Nonnull EnumTankType type) {
        this.type = type;
    }

    @Nonnull
    @Override
    protected TileEntityMoltenMetalTankGuiManager getInitialGuiManager() {
        return new TileEntityMoltenMetalTankGuiManager();
    }

    @Nonnull
    @Override
    protected TileEntityMoltenMetalTankState getInitialState() {
        return new TileEntityMoltenMetalTankState();
    }

    @Nonnull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Tank + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.type = EnumTankType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE));

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE, type.getMetadata());

        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }

    @Override
    public Object getCapability(Capability capability, EnumFacing facing) {
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {

    }

    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        return null;
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        return 0;
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        return 0;
    }
}
