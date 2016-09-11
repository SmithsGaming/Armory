package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.conduit.ConduitFluidTank;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Author Orion (Created on: 07.08.2016)
 */
public class StructureDataConduit implements IStructureData<StructureConduit> {

    EnumConduitType structureType;
    ConduitFluidTank structureTank;

    HashMap<Coordinate3D, HashSet<EnumFacing>> inputConnectionSides = new HashMap<>();
    HashMap<Coordinate3D, HashSet<EnumFacing>> outputConnectionSides = new HashMap<>();

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

        NBTTagList inputList = new NBTTagList();
        NBTTagList outputList = new NBTTagList();

        for (Map.Entry<Coordinate3D, HashSet<EnumFacing>> input : inputConnectionSides.entrySet()) {
            NBTTagCompound inputCompound = new NBTTagCompound();
            inputCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.COORDINATE, input.getKey().toCompound());

            NBTTagList faceList = new NBTTagList();
            for (EnumFacing facing : input.getValue()) {
                faceList.appendTag(new NBTTagInt(facing.getIndex()));
            }

            inputCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.SIDES, faceList);

            inputList.appendTag(inputCompound);
        }

        for (Map.Entry<Coordinate3D, HashSet<EnumFacing>> output : outputConnectionSides.entrySet()) {
            NBTTagCompound outputCompound = new NBTTagCompound();
            outputCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.COORDINATE, output.getKey().toCompound());

            NBTTagList faceList = new NBTTagList();
            for (EnumFacing facing : output.getValue()) {
                faceList.appendTag(new NBTTagInt(facing.getIndex()));
            }

            outputCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.SIDES, faceList);

            outputList.appendTag(outputCompound);
        }

        compound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.INPUTCONNECTIONS, inputList);
        compound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.OUTPUTCONNECTIONS, outputList);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        structureTank.deserializeNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.FLUIDS));
        structureType = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));

        NBTTagList inputList = compound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.INPUTCONNECTIONS, Constants.NBT.TAG_COMPOUND);
        NBTTagList outputList = compound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.OUTPUTCONNECTIONS, Constants.NBT.TAG_COMPOUND);

        inputConnectionSides.clear();
        outputConnectionSides.clear();

        for (int i = 0; i < inputList.tagCount(); i++) {
            NBTTagCompound inputCompound = inputList.getCompoundTagAt(i);

            Coordinate3D coordinate = Coordinate3D.fromNBT(inputCompound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.COORDINATE));

            HashSet<EnumFacing> facingList = new HashSet<>();
            NBTTagList faceList = inputCompound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.SIDES, Constants.NBT.TAG_INT);
            for (int f = 0; f < faceList.tagCount(); f++) {
                int index = faceList.getIntAt(f);
                facingList.add(EnumFacing.VALUES[index]);
            }

            inputConnectionSides.put(coordinate, facingList);
        }

        for (int i = 0; i < outputList.tagCount(); i++) {
            NBTTagCompound outputCompound = inputList.getCompoundTagAt(i);

            Coordinate3D coordinate = Coordinate3D.fromNBT(outputCompound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.COORDINATE));

            HashSet<EnumFacing> facingList = new HashSet<>();
            NBTTagList faceList = outputCompound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.Connections.SIDES, Constants.NBT.TAG_INT);
            for (int f = 0; f < faceList.tagCount(); f++) {
                int index = faceList.getIntAt(f);
                facingList.add(EnumFacing.VALUES[index]);
            }

            outputConnectionSides.put(coordinate, facingList);
        }
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

    public void addInputConnection(Coordinate3D connectionCoordinate, EnumFacing facing) {
        if (!inputConnectionSides.containsKey(connectionCoordinate))
            inputConnectionSides.put(connectionCoordinate, new HashSet<>());

        inputConnectionSides.get(connectionCoordinate).add(facing);
    }

    public void removeInputConnection(Coordinate3D connectionCoordinate, EnumFacing facing) {
        if (!inputConnectionSides.containsKey(connectionCoordinate))
            return;

        inputConnectionSides.get(connectionCoordinate).remove(facing);

        if (inputConnectionSides.get(connectionCoordinate).size() == 0)
            inputConnectionSides.remove(connectionCoordinate);
    }

    public void addOutputConnection(Coordinate3D connectionCoordinate, EnumFacing facing) {
        if (!outputConnectionSides.containsKey(connectionCoordinate))
            outputConnectionSides.put(connectionCoordinate, new HashSet<>());

        outputConnectionSides.get(connectionCoordinate).add(facing);
    }

    public void removeOutputConnection(Coordinate3D connectionCoordinate, EnumFacing facing) {
        if (!outputConnectionSides.containsKey(connectionCoordinate))
            return;

        outputConnectionSides.get(connectionCoordinate).remove(facing);

        if (outputConnectionSides.get(connectionCoordinate).size() == 0)
            outputConnectionSides.remove(connectionCoordinate);
    }

    
}
