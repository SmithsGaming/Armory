package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.moltenmetal.MoltenMetalTank;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by marcf on 2/7/2017.
 */
public class StructureDataConduitNetwork implements IStructureData<StructureConduitNetwork> {

    private EnumConduitType networkType = EnumConduitType.LIGHT;
    private MoltenMetalTank networkTank = new MoltenMetalTank(References.General.FLUID_INGOT, 1);
    private StructureConduitNetwork network;

    public void assignToNetwork(StructureConduitNetwork network) {
        this.network = network;
        updateTank();
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound dataCompound = new NBTTagCompound();
        dataCompound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE, networkType.getMetadata());
        dataCompound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDSIZE, networkTank.getCapacity());
        dataCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDS, networkTank.serializeNBT());

        return dataCompound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        networkType = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));

        networkTank.deserializeNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDS));
    }

    @Override
    public void onDataMergeInto(@Nonnull IStructureData<StructureConduitNetwork> otherData) {
        MoltenMetalTank otherTank = ((StructureDataConduitNetwork) otherData).networkTank;

        int totalTankCapacity = otherTank.getCapacity() + networkTank.getCapacity();

        if (otherTank.getFluidAmount() == 0) {
            FluidStack stack = networkTank.getFluid();
            if (stack != null)
                networkTank = new MoltenMetalTank(totalTankCapacity, 1, stack);
            else
                networkTank = new MoltenMetalTank(totalTankCapacity, 1, new ArrayList<>());
        } else {
            FluidStack stack = otherTank.getFluid();
            if (stack != null)
                networkTank = new MoltenMetalTank(totalTankCapacity, 1, stack);
            else
                networkTank = new MoltenMetalTank(totalTankCapacity, 1, new ArrayList<>());
        }
    }

    public EnumConduitType getNetworkType() {
        return networkType;
    }

    public MoltenMetalTank getNetworkTank() {
        return networkTank;
    }

    public void updateTank() {
        ArrayList<FluidStack> stacks = new ArrayList<>(networkTank.getFluidStacks());
        networkTank = new MoltenMetalTank(network.getPartLocations().size() * References.General.FLUID_INGOT, 1, stacks);
    }
}
