package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;

import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 07.08.2016)
 */
public class StructureConduit implements IStructure<StructureDataConduit, StructureControllerConduit, TileEntityConduit> {

    StructureDataConduit data;
    StructureControllerConduit controller;
    private LinkedHashSet<Coordinate3D> parts;
    private Coordinate3D masterLocation;

    public StructureConduit(LinkedHashSet<Coordinate3D> parts, EnumConduitType type) {
        this.data = new StructureDataConduit(type);
        this.controller = new StructureControllerConduit();
        this.parts = parts;
        this.masterLocation = parts.iterator().next();

        this.data.onAssignToConduit(this);
    }

    @Override
    public StructureControllerConduit getController() {
        return controller;
    }

    @Override
    public StructureDataConduit getData() {
        return data;
    }

    @Override
    public boolean canPartJoin(IStructurePart part) {
        if (part instanceof TileEntityConduit) {
            TileEntityConduit conduit = (TileEntityConduit) part;

            return conduit.getType() == getData().getStructureType();
        }

        return false;
    }

    @Override
    public LinkedHashSet<Coordinate3D> getPartLocations() {
        return parts;
    }

    @Override
    public void registerPart(TileEntityConduit part) {
        parts.add(part.getLocation());
        this.data.onStructureSizeChanged(this);
    }

    @Override
    public void removePart(TileEntityConduit part) {
        parts.remove(part.getLocation());
        this.data.onStructureSizeChanged(this);
    }

    @Override
    public Coordinate3D getMasterLocation() {
        return masterLocation;
    }

    @Override
    public void setMasterLocation(Coordinate3D masterLocation) {
        this.masterLocation = masterLocation;
    }
}
