package com.smithsmodding.armory.common.structure.forge;

import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureForge implements IStructure<StructureDataForge, StructureControllerForge, TileEntityForge> {

    private static final StructureControllerForge controller = new StructureControllerForge();
    private final StructureDataForge dataForge;
    private final LinkedHashSet<Coordinate3D> parts;
    private Coordinate3D masterLocation;

    public StructureForge(@NotNull TileEntityForge master) {
        masterLocation = master.getLocation();
        dataForge = new StructureDataForge();
        parts = new LinkedHashSet<>();

        this.dataForge.onAssignToForge(this);
    }

    protected StructureForge(StructureDataForge dataForge, LinkedHashSet<Coordinate3D> parts) {
        this.dataForge = dataForge;
        this.parts = parts;

        this.dataForge.onAssignToForge(this);
    }

    @NotNull
    @Override
    public StructureControllerForge getController() {
        return controller;
    }

    @Override
    public StructureDataForge getData() {
        return dataForge;
    }

    @Override
    public boolean canPartJoin(IStructurePart part) {
        return part instanceof TileEntityForge;
    }

    @Override
    public LinkedHashSet<Coordinate3D> getPartLocations() {
        return parts;
    }

    @Override
    public void registerPart(@NotNull TileEntityForge part) {
        parts.add(part.getLocation());
        getData().updateTank();
    }

    @Override
    public void removePart(@NotNull TileEntityForge part) {
        parts.remove(part.getLocation());
        getData().updateTank();
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
