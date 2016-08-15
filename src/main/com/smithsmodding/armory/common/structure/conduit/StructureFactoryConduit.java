package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.common.structures.IStructureFactory;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 07.08.2016)
 */
public class StructureFactoryConduit implements IStructureFactory<StructureConduit, TileEntityConduit> {
    @Override
    public StructureConduit generateNewStructure(TileEntityConduit initialPart) {
        LinkedHashSet<Coordinate3D> parts = new LinkedHashSet<>();
        parts.add(initialPart.getLocation());

        return new StructureConduit(parts, initialPart.getType());
    }

    @Override
    public StructureConduit loadStructureFromNBT(NBTTagCompound compound) {
        EnumConduitType type = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));

        LinkedHashSet<Coordinate3D> parts = new LinkedHashSet<>();
        NBTTagList coordinates = compound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.PARTS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < coordinates.tagCount(); i++) {
            Coordinate3D location = Coordinate3D.fromNBT(coordinates.getCompoundTagAt(i));
            parts.add(location);
        }

        StructureConduit conduit = new StructureConduit(parts, type);
        conduit.getData().readFromNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.DATA));

        return conduit;
    }

    @Override
    public NBTTagCompound generateNBTFromStructure(StructureConduit structure) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.DATA, structure.getData().writeToNBT());

        NBTTagList coordinates = new NBTTagList();
        for (Coordinate3D location : structure.getPartLocations()) {
            coordinates.appendTag(location.toCompound());
        }

        compound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.PARTS, coordinates);
        compound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE, structure.getData().structureType.getMetadata());

        return compound;
    }

    @Override
    public Class<StructureConduit> getStructureType() {
        return StructureConduit.class;
    }
}
