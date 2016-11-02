package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.state.TileEntityConduitState;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class TileEntityConduit extends TileEntitySmithsCore<TileEntityConduitState, TileStorageBasedGUIManager> implements ITickable {

    EnumConduitType type;

    ArrayList<EnumFacing> connectedSides = new ArrayList<>();

    public TileEntityConduit() {
    }

    public TileEntityConduit(EnumConduitType type) {
        this();

        this.type = type;
    }

    @NotNull
    @Override
    protected TileStorageBasedGUIManager getInitialGuiManager() {
        return new TileStorageBasedGUIManager();
    }

    @NotNull
    @Override
    protected TileEntityConduitState getInitialState() {
        return new TileEntityConduitState();
    }

    @NotNull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Conduit + "-" + getLocation().toString();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.type = EnumConduitType.byMetadata(compound.getInteger(References.NBTTagCompoundData.TE.Conduit.Structure.TYPE));
    }

    @NotNull
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
}
