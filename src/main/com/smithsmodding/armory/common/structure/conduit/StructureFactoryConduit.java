package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.common.structures.IStructureFactory;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 2/7/2017.
 */
public class StructureFactoryConduit implements IStructureFactory<StructureConduitNetwork, TileEntityConduit> {
    @Nonnull
    @Override
    public StructureConduitNetwork generateNewStructure(@Nonnull TileEntityConduit initialPart) {
        StructureConduitNetwork network = new StructureConduitNetwork();
        network.setMasterLocation(initialPart.getLocation());

        return network;
    }

    @Nonnull
    @Override
    public StructureConduitNetwork loadStructureFromNBT(@Nonnull NBTTagCompound compound) {
        StructureConduitNetwork network = new StructureConduitNetwork();

        NBTTagList coordinates = compound.getTagList(References.NBTTagCompoundData.TE.Conduit.Structure.PARTS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < coordinates.tagCount(); i++) {
            Coordinate3D location = Coordinate3D.fromNBT(coordinates.getCompoundTagAt(i));
            network.getPartLocations().add(location);
        }

        network.getData().readFromNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Conduit.Structure.DATA));

        return network;
    }

    @Nonnull
    @Override
    public NBTTagCompound generateNBTFromStructure(@Nonnull StructureConduitNetwork structure) {
        NBTTagCompound structureCompound = new NBTTagCompound();
        NBTTagList partList = new NBTTagList();
        NBTTagCompound dataCompound;

        structure.getPartLocations().forEach(coordinate3D -> {
            partList.appendTag(coordinate3D.toCompound());
        });

        dataCompound = structure.getData().writeToNBT();

        structureCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.PARTS, partList);
        structureCompound.setTag(References.NBTTagCompoundData.TE.Conduit.Structure.DATA, dataCompound);

        return structureCompound;
    }

    @Nonnull
    @Override
    public Class<StructureConduitNetwork> getStructureType() {
        return StructureConduitNetwork.class;
    }
}
