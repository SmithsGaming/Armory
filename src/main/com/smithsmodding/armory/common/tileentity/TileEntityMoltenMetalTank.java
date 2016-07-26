package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityMoltenMetalTankGuiManager;
import com.smithsmodding.armory.common.tileentity.moltenmetal.MoltenMetalTank;
import com.smithsmodding.armory.common.tileentity.state.TileEntityMoltenMetalTankState;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class TileEntityMoltenMetalTank extends TileEntitySmithsCore implements ITickable {

    private MoltenMetalTank tank;
    private EnumTankType type;

    protected TileEntityMoltenMetalTank() {
    }

    public TileEntityMoltenMetalTank(EnumTankType type) {
        this.tank = new MoltenMetalTank(type.getTankContents(), 1);
        this.type = type;
    }

    @Override
    protected IGUIManager getInitialGuiManager() {
        return new TileEntityMoltenMetalTankGuiManager();
    }

    @Override
    protected ITileEntityState getInitialState() {
        return new TileEntityMoltenMetalTankState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Tank + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.type = EnumTankType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE));
        this.tank = new MoltenMetalTank(type.getTankContents(), 1);

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(References.NBTTagCompoundData.TE.MoltenMetalTank.TYPE, type.getMetadata());

        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public Object getCapability(Capability capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY || capability == ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY)
            return tank;

        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            TileEntity entity = getWorld().getTileEntity(pos.offset(facing));

            if (entity == null)
                continue;

            if (entity.hasCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite())) {
                IMoltenMetalRequester requester = entity.getCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite());

                if (tank.getFluidAmount() <= 0)
                    return;

                FluidStack outputStack = tank.getFluidStacks().get(0);
                outputStack.amount -= requester.fillNext(outputStack, true);

                if (outputStack.amount <= 0)
                    tank.getFluidStacks().remove(0);
            }
        }
    }
}
