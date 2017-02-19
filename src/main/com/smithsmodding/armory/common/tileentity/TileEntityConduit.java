package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.common.fluid.IMoltenMetalAcceptor;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.structure.conduit.StructureConduitNetwork;
import com.smithsmodding.armory.common.tileentity.moltenmetal.MoltenMetalTank;
import com.smithsmodding.armory.common.tileentity.state.TileEntityConduitState;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.common.events.structure.StructureEvent;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class TileEntityConduit extends TileEntitySmithsCore<TileEntityConduitState, TileStorageBasedGUIManager> implements ITickable, IStructurePart<StructureConduitNetwork>, IFluidContainingEntity {

    EnumConduitType type;
    Coordinate3D network;
    ArrayList<EnumFacing> connectedSides = new ArrayList<>();

    int clientNetworkRebuildCooldown = 0;

    public TileEntityConduit() {
    }

    public TileEntityConduit(EnumConduitType type) {
        this();

        this.type = type;
    }

    @Nonnull
    @Override
    protected TileStorageBasedGUIManager getInitialGuiManager() {
        return new TileStorageBasedGUIManager();
    }

    @Nonnull
    @Override
    protected TileEntityConduitState getInitialState() {
        return new TileEntityConduitState();
    }

    @Nonnull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Conduit + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.type = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE, type.getMetadata());

        return compound;
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    public void update() {
        if (isRemote()) {
            if (clientNetworkRebuildCooldown == 0 && network == null) {
                setStructure((StructureConduitNetwork) StructureRegistry.getClientInstance().getStructure(getWorld().provider.getDimension(), getLocation()));
                clientNetworkRebuildCooldown = 20;
            } else if (clientNetworkRebuildCooldown > 0 && network == null) {
                clientNetworkRebuildCooldown--;
            }

            return;
        }

        if (getStructure() == null)
            return;

        if (getStructure().getData() == null)
            return;

        final MoltenMetalTank internalTank = getStructure().getData().getNetworkTank();

        if (internalTank.getFluidAmount() == 0) {
            return;
        }

        if (getType() == EnumConduitType.VERTICAL) {
            TileEntity tileEntityAbove = getWorld().getTileEntity(getPos().offset(EnumFacing.UP));

            if (tileEntityAbove == null){
                return;
            }

            if (tileEntityAbove instanceof TileEntityConduit) {
                TileEntityConduit conduit = (TileEntityConduit) tileEntityAbove;
                if (conduit.getType() != EnumConduitType.VERTICAL) {
                    MoltenMetalTank tankAbove = conduit.getStructure().getData().getNetworkTank();

                    FluidStack drain = internalTank.drain(Integer.MAX_VALUE, false);
                    if (drain == null || drain.amount == 0){
                        return;
                    }

                    int drained = tankAbove.fill(drain, true);
                    if (drained == 0){
                        return;
                    }

                    internalTank.drain(drained, true);
                    conduit.markDirty();
                }
            } else {
                if (tileEntityAbove.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.DOWN)) {
                    IMoltenMetalAcceptor acceptor = tileEntityAbove.getCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.DOWN);

                    FluidStack drain = internalTank.drain(Integer.MAX_VALUE, false);
                    if (drain == null || drain.amount == 0){
                        return;
                    }

                    FluidStack drained = acceptor.acceptMetal(drain, false);
                    if (drained == null || drained.amount == 0){
                        return;
                    }

                    internalTank.drain(drained, true);
                    tileEntityAbove.markDirty();
                }
            }
        } else {
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(facing));

                if (tileEntity == null)
                    continue;

                if (tileEntity instanceof TileEntityConduit)
                    continue;

                if (tileEntity.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, facing.getOpposite())) {
                    IMoltenMetalAcceptor acceptor = tileEntity.getCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, facing.getOpposite());

                    FluidStack drain = internalTank.drain(Integer.MAX_VALUE, false);
                    if (drain == null || drain.amount == 0)
                        continue;

                    FluidStack drained = acceptor.acceptMetal(drain, false);
                    if (drained == null || drained.amount == 0)
                        continue;

                    internalTank.drain(drained, true);

                    tileEntity.markDirty();
                }
            }

            TileEntity tileEntityBelow = getWorld().getTileEntity(getPos().offset(EnumFacing.DOWN));

            if (tileEntityBelow instanceof TileEntityConduit) {
                TileEntityConduit conduit = (TileEntityConduit) tileEntityBelow;
                if (conduit.getType() != EnumConduitType.VERTICAL) {
                    MoltenMetalTank tankBelow = conduit.getStructure().getData().getNetworkTank();

                    FluidStack drain = internalTank.drain(Integer.MAX_VALUE, false);
                    if (drain == null || drain.amount == 0){
                        return;
                    }

                    int drained = tankBelow.fill(drain, true);
                    if (drained == 0){
                        return;
                    }

                    internalTank.drain(drained, true);
                    conduit.markDirty();
                }
            } else if (tileEntityBelow != null) {
                if (tileEntityBelow.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.DOWN)) {
                    IMoltenMetalAcceptor acceptor = tileEntityBelow.getCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.DOWN);

                    FluidStack drain = internalTank.drain(Integer.MAX_VALUE, false);
                    if (drain == null || drain.amount == 0){
                        return;
                    }

                    FluidStack drained = acceptor.acceptMetal(drain, false);
                    if (drained == null || drained.amount == 0){
                        return;
                    }

                    internalTank.drain(drained, true);
                    tileEntityBelow.markDirty();
                }
            }
        }

        this.markDirty();
    }

    /**
     * Method to indicate that this TE has changed its data.
     */
    @Override
    public void markDirty() {
        new StructureEvent.Updated(getStructure(), getWorld().provider.getDimension()).PostCommon();
        super.markDirty();
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY || capability == ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY)
            return getStructure() != null && getStructure().getData() != null;

        return super.hasCapability(capability, facing);
    }

    @Override
    public Object getCapability(Capability capability, EnumFacing facing) {
        if (capability == ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY || capability == ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY)
            return getStructure().getData().getNetworkTank();

        return super.getCapability(capability, facing);
    }


    public ArrayList<EnumFacing> getConnectedSides() {
        return connectedSides;
    }

    public void setConnectedSides(ArrayList<EnumFacing> connectedSides) {
        this.connectedSides = connectedSides;
    }

    public EnumConduitType getType() {
        return type;
    }

    @Nonnull
    @Override
    public Class<StructureConduitNetwork> getStructureType() {
        return StructureConduitNetwork.class;
    }

    @Nullable
    @Override
    public StructureConduitNetwork getStructure() {
        if (network == null) {
            return null;
        }

        return (StructureConduitNetwork) StructureRegistry.getInstance().getStructure(getWorld().provider.getDimension(), network);
    }

    @Override
    public void setStructure(@Nullable StructureConduitNetwork structure) {
        if (structure == null) {
            this.network = null;
            return;
        }

        this.network = structure.getMasterLocation();
    }

    @Nonnull
    @Override
    public World getEnvironment() {
        return getWorld();
    }

    @Nonnull
    @Override
    public ArrayList<IPathComponent> getValidPathableNeighborComponents() {
        if (getStructure().getData().getNetworkType() == EnumConduitType.VERTICAL)
            return getValidPathableNeighborComponentsVertical();

        return getValidPathableNeighborComponentsNormal();
    }

    private ArrayList<IPathComponent> getValidPathableNeighborComponentsNormal() {
        ArrayList<IPathComponent> pathComponentArrayList = new ArrayList<>();

        for (EnumFacing facing : EnumFacing.values()) {
            if ((facing == EnumFacing.UP) || (facing == EnumFacing.DOWN))
                continue;

            TileEntity entity = getWorld().getTileEntity(getLocation().moveCoordinate(facing, 1).toBlockPos());
            if (entity == null)
                continue;

            if (!(entity instanceof IStructurePart))
                continue;

            if (((IStructurePart) entity).getStructure() == null)
                continue;

            pathComponentArrayList.add((IPathComponent) entity);
        }

        return pathComponentArrayList;
    }

    private ArrayList<IPathComponent> getValidPathableNeighborComponentsVertical() {
        ArrayList<IPathComponent> pathComponentArrayList = new ArrayList<>();

        for (EnumFacing facing : EnumFacing.values()) {
            if (!((facing == EnumFacing.UP) || (facing == EnumFacing.DOWN)))
                continue;

            TileEntity entity = getWorld().getTileEntity(getLocation().moveCoordinate(facing, 1).toBlockPos());
            if (entity == null)
                continue;

            if (!(entity instanceof IStructurePart))
                continue;

            if (((IStructurePart) entity).getStructure() == null)
                continue;

            pathComponentArrayList.add((IPathComponent) entity);
        }

        return pathComponentArrayList;
    }


    @Override
    public boolean requiresNBTStorage(EnumFacing side) {
        return false;
    }

    @Nonnull
    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        if (getStructure() == null || getStructure().getData() == null)
            return new MoltenMetalTank(1, 0);

        return getStructure().getData().getNetworkTank();
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        if (getTankForSide(side) == null)
            return 0;

        return getTankForSide(side).getCapacity();
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        if (getTankForSide(side) == null)
            return 0;

        return getTankForSide(side).getFluidAmount();
    }
}
