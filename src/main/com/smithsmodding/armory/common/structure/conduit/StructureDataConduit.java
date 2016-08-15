package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 07.08.2016)
 */
public class StructureDataConduit implements IStructureData<StructureConduit> {

    EnumConduitType structureType;
    ConduitFluidTank structureTank;

    StructureConduit structureConduit;

    public StructureDataConduit(EnumConduitType type) {
        this.structureType = type;
    }

    public void onAssignToConduit(StructureConduit structureConduit) {
        this.structureConduit = structureConduit;
        this.structureTank = new ConduitFluidTank(structureConduit.getPartLocations().size() * References.General.FLUID_INGOT);
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDS, structureTank.serializeNBT());
        compound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE, structureType.getMetadata());

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        structureTank.deserializeNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDS));
        structureType = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));
    }

    @Override
    public void onDataMergeInto(IStructureData<StructureConduit> otherData) {
        if (otherData instanceof StructureDataConduit) {
            StructureDataConduit data = (StructureDataConduit) otherData;

            if (data.getStructureTank().getFluidAmount() == 0) {
                structureTank.updateSize((structureConduit.getPartLocations().size() + data.structureConduit.getPartLocations().size()) * References.General.FLUID_INGOT);
                return;
            }

            this.structureTank = new ConduitFluidTank((structureConduit.getPartLocations().size() + data.structureConduit.getPartLocations().size()) * References.General.FLUID_INGOT);
        }
    }

    public void onStructureSizeChanged(StructureConduit conduit) {
        this.getStructureTank().updateSize(conduit.getPartLocations().size() * References.General.FLUID_INGOT);
    }

    public EnumConduitType getStructureType() {
        return structureType;
    }

    public ConduitFluidTank getStructureTank() {
        return structureTank;
    }
}
