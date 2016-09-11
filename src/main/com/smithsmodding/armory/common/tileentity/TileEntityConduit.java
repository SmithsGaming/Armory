package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.common.MoltenMetalHelper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.structure.conduit.StructureConduit;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityConduitGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityConduitState;
import com.smithsmodding.smithscore.common.events.structure.StructureEvent;
import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class TileEntityConduit extends TileEntitySmithsCore<TileEntityConduitState, TileEntityConduitGuiManager> implements ITickable, IStructurePart<StructureConduit> {

    EnumConduitType type;

    ArrayList<EnumFacing> connectedSides = new ArrayList<>();
    HashSet<TileEntity> pushedOutputs = new HashSet<>();
    private Coordinate3D masterLocation;

    public TileEntityConduit() {
    }

    public TileEntityConduit(EnumConduitType type) {
        this();

        this.type = type;
    }

    @Override
    protected TileEntityConduitGuiManager getInitialGuiManager() {
        return new TileEntityConduitGuiManager();
    }

    @Override
    protected TileEntityConduitState getInitialState() {
        return new TileEntityConduitState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Conduit + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.type = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE, type.getMetadata());

        return compound;
    }

    @Override
    public void update() {
        if (isRemote()) {
            return;
        }

        if (this.getStructure() == null)
            return;

        this.pushedOutputs.clear();

        this.getWorld().theProfiler.startSection("push");
        handlePushToRequesters();
        this.getWorld().theProfiler.endSection();

        this.getWorld().theProfiler.startSection("pull");
        handlePullFromProviders();
        this.getWorld().theProfiler.endSection();

        this.getWorld().theProfiler.startSection("down");
        handlePushDownwards();
        this.getWorld().theProfiler.endSection();

        this.getWorld().theProfiler.startSection("sync");
        new StructureEvent.Updated(getStructure(), getWorld().provider.getDimension()).PostCommon();

        markDirty();
        this.getWorld().theProfiler.endSection();
    }

    public void handlePushToRequesters() {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.UP)
                continue;

            TileEntity entity = getWorld().getTileEntity(getPos().offset(facing));

            if (entity == null || !entity.hasCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite()))
                continue;

            int contentsBefore = getStructure().getData().getStructureTank().getFluidAmount();
            MoltenMetalHelper.transferMaxAmount(getStructure().getData().getStructureTank(), entity.getCapability(ModCapabilities.MOLTEN_METAL_REQUESTER_CAPABILITY, facing.getOpposite()));
            if (contentsBefore != getStructure().getData().getStructureTank().getFluidAmount())
                pushedOutputs.add(entity);
        }
    }

    public void handlePullFromProviders() {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.UP)
                continue;

            TileEntity entity = getWorld().getTileEntity(getPos().offset(facing));

            if (entity == null || !entity.hasCapability(ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY, facing.getOpposite()))
                continue;

            if (pushedOutputs.contains(entity))
                continue;

            MoltenMetalHelper.transferMaxAmount(entity.getCapability(ModCapabilities.MOLTEN_METAL_PROVIDER_CAPABILITY, facing.getOpposite()), getStructure().getData().getStructureTank());
        }
    }

    public void handlePushDownwards() {
        TileEntity entity = getWorld().getTileEntity(getPos().offset(EnumFacing.DOWN));

        if (entity instanceof TileEntityConduit) {
            TileEntityConduit conduit = (TileEntityConduit) entity;

            MoltenMetalHelper.transferMaxAmount(getStructure().getData().getStructureTank(), conduit.getStructure().getData().getStructureTank());

            new StructureEvent.Updated(conduit.getStructure(), getWorld().provider.getDimension());
        }
    }

    public ArrayList<EnumFacing> getConnectedSides() {
        return connectedSides;
    }

    public void setConnectedSides(ArrayList<EnumFacing> connectedSides) {
        this.connectedSides = connectedSides;
    }

    @Override
    public Class<StructureConduit> getStructureType() {
        return StructureConduit.class;
    }

    @Override
    public StructureConduit getStructure() {
        return (StructureConduit) StructureRegistry.getInstance().getStructure(getWorld().provider.getDimension(), masterLocation);
    }

    @Override
    public void setStructure(StructureConduit structure) {
        if (structure == null)
            return;

        this.masterLocation = structure.getMasterLocation();
    }

    @Override
    public World getEnvironment() {
        return getWorld();
    }

    @Override
    public ArrayList<IPathComponent> getValidPathableNeighborComponents() {
        ArrayList<IPathComponent> components = new ArrayList<>();

        for (EnumFacing facing : getStructure().getController().getPossibleConnectionSides()) {
            TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(facing));

            if (tileEntity instanceof TileEntityConduit) {
                TileEntityConduit other = (TileEntityConduit) tileEntity;

                if (type == other.type)
                    components.add(other);
            }
        }

        return components;
    }

    public EnumConduitType getType() {
        return type;
    }
}
