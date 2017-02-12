package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;

/**
 * Created by marcf on 2/7/2017.
 */
public class StructureConduitNetwork implements IStructure<StructureDataConduitNetwork, StructureControllerConduitNetwork, TileEntityConduit> {

    private static final StructureControllerConduitNetwork controller = new StructureControllerConduitNetwork();
    private final StructureDataConduitNetwork dataConduitNetwork = new StructureDataConduitNetwork();
    private final LinkedHashSet<Coordinate3D> parts = new LinkedHashSet<>();
    private Coordinate3D masterLocation;

    public StructureConduitNetwork() {
        this.dataConduitNetwork.assignToNetwork(this);
    }

    @Nonnull
    @Override
    public StructureControllerConduitNetwork getController() {
        return controller;
    }

    @Nonnull
    @Override
    public StructureDataConduitNetwork getData() {
        return dataConduitNetwork;
    }

    @Override
    public boolean canPartJoin(@Nonnull IStructurePart part) {
        if (getData().getNetworkType() == EnumConduitType.VERTICAL)
            if (part.getLocation().getXComponent() != masterLocation.getXComponent() || part.getLocation().getZComponent() != part.getLocation().getZComponent())
                return false;
        else if (part.getLocation().getYComponent() != masterLocation.getYComponent())
            return false;

        StructureConduitNetwork otherNetwork = (StructureConduitNetwork) part.getStructure();
        StructureDataConduitNetwork otherData = otherNetwork.getData();

        if (otherData.getNetworkType() != getData().getNetworkType())
            return false;

        return otherData.getNetworkTank().getFluidAmount() == 0 || getData().getNetworkTank().getFluidAmount() == 0;
    }

    @Nonnull
    @Override
    public LinkedHashSet<Coordinate3D> getPartLocations() {
        return parts;
    }

    @Override
    public void registerPart(@Nonnull TileEntityConduit part) {
        parts.add(part.getLocation());
        getData().updateTank();
    }

    @Override
    public void removePart(@Nonnull TileEntityConduit part) {
        parts.remove(part.getLocation());
        getData().updateTank();
    }

    @Nonnull
    @Override
    public Coordinate3D getMasterLocation() {
        return masterLocation;
    }

    @Override
    public void setMasterLocation(@Nonnull Coordinate3D masterLocation) {
        this.masterLocation = masterLocation;
    }
}
